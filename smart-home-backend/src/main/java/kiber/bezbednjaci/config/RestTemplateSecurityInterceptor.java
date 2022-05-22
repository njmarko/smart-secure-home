package kiber.bezbednjaci.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
public class RestTemplateSecurityInterceptor implements ClientHttpRequestInterceptor {
    private final HttpServletRequest httpServletRequest;

    public RestTemplateSecurityInterceptor(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public ClientHttpResponse intercept(@NonNull HttpRequest request, @NonNull byte[] body, @NonNull ClientHttpRequestExecution execution) throws IOException {
        getAuthCookie().ifPresent(cookie -> {
            request.getHeaders().add("Cookie", httpServletRequest.getHeader("Cookie"));
        });
        request.getHeaders().add("Authorization", httpServletRequest.getHeader("Authorization"));
        return execution.execute(request, body);
    }

    private Optional<Cookie> getAuthCookie() {
        return Arrays.stream(httpServletRequest.getCookies()).filter(cookie -> cookie.getName().equals("Fingerprint")).findAny();
    }
}
