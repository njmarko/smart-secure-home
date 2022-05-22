package kiber.bezbednjaci.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.HttpServletRequest;
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
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        var interceptors = Objects.requireNonNullElse(restTemplate.getInterceptors(), new ArrayList<ClientHttpRequestInterceptor>());
        interceptors.add(restTemplateSecurityInterceptor);
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }
}
