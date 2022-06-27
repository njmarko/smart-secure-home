package kiber.bezbednjaci.config;


import kiber.bezbednjaci.bus.EventBus;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.drools.core.ClockType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Objects;

import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;

@Configuration
@Slf4j
public class BeanConfig {

    @Bean
    @RequestScope
    public RestTemplateSecurityInterceptor interceptor(HttpServletRequest httpServletRequest) {
        return new RestTemplateSecurityInterceptor(httpServletRequest);
    }

    @Bean
    @RequestScope
    public RestTemplate restTemplate(RestTemplateSecurityInterceptor restTemplateSecurityInterceptor) {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
        SSLContext sslContext = null;
        try {
            sslContext = org.apache.http.ssl.SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy)
                    .build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            e.printStackTrace();
        }
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(csf)
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        java.util.List<ClientHttpRequestInterceptor> interceptors = Objects.requireNonNullElse(restTemplate.getInterceptors(), new ArrayList<ClientHttpRequestInterceptor>());
        interceptors.add(restTemplateSecurityInterceptor);
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

    @Bean
    public KieSession kieSession(EventBus eventBus) {
        log.info("Starting template compilation...");


        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks
                .newKieContainer(ks.newReleaseId("rs.bsep", "device-rules-kjar", "0.0.1-SNAPSHOT"));
        KieBaseConfiguration kieBaseConfiguration = ks.newKieBaseConfiguration();
        kieBaseConfiguration.setOption(EventProcessingOption.STREAM);
        KieBase kieBase = kContainer.newKieBase(kieBaseConfiguration);

        KieSessionConfiguration kieSessionConfiguration = ks.newKieSessionConfiguration();
        kieSessionConfiguration.setOption(ClockTypeOption.get(ClockType.REALTIME_CLOCK.getId()));
        KieSession kieSession = kieBase.newKieSession(kieSessionConfiguration, null);

//        kieSession.setGlobal("eventBus", eventBus);

        return kieSession;
    }
}
