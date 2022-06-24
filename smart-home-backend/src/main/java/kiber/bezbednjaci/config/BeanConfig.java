package kiber.bezbednjaci.config;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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

@Configuration
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

        var interceptors = Objects.requireNonNullElse(restTemplate.getInterceptors(), new ArrayList<ClientHttpRequestInterceptor>());
        interceptors.add(restTemplateSecurityInterceptor);
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }
}
