package kiber.bezbednjaci.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;

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
        if (Objects.isNull(httpServletRequest.getCookies())) {
            return Optional.empty();
        }
        return Arrays.stream(httpServletRequest.getCookies()).filter(cookie -> cookie.getName().equals("Fingerprint")).findAny();
    }
}
