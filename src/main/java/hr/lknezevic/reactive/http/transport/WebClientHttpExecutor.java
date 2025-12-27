package hr.lknezevic.reactive.http.transport;

import hr.lknezevic.reactive.http.action.HttpRequestSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public final class WebClientHttpExecutor implements HttpExecutor {
    private final static List<HttpMethod> NOT_SUPPORTED_HTTP_METHODS =
            List.of(HttpMethod.HEAD, HttpMethod.TRACE, HttpMethod.OPTIONS);
    private final WebClient webClient;

    @Override
    public <T> Mono<T> execute(HttpRequestSpec<T> requestSpec) {
        if (NOT_SUPPORTED_HTTP_METHODS.contains(requestSpec.httpMethod())) {
            throw new UnsupportedOperationException("HTTP method is not currently supported: " + requestSpec.httpMethod().name());
        }

        var req = webClient
                .method(requestSpec.httpMethod())
                .uri(requestSpec.uri());

        if (requestSpec.body() != null) {
            req.bodyValue(requestSpec.body());
        }

        return req.exchangeToMono(r -> r.bodyToMono(requestSpec.responseType()));
    }
}
