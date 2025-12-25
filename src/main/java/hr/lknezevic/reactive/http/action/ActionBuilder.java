package hr.lknezevic.reactive.http.action;

import hr.lknezevic.reactive.http.transport.HttpExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.Objects;

@RequiredArgsConstructor
public final class ActionBuilder {
    private final HttpExecutor httpExecutor;

    private HttpMethod httpMethod;
    private String uri;
    private Object body;
    private ParameterizedTypeReference<?> responseType;

    public ActionBuilder get(String uri) {
        return method(HttpMethod.GET, uri, null);
    }

    public ActionBuilder delete(String uri) {
        return method(HttpMethod.DELETE, uri, null);
    }

    public ActionBuilder post(String uri, Object body) {
        return method(HttpMethod.POST, uri, body);
    }

    public ActionBuilder put(String uri, Object body) {
        return method(HttpMethod.PUT, uri, body);
    }

    public ActionBuilder patch(String uri, Object body) {
        return method(HttpMethod.PATCH, uri, body);
    }

    private ActionBuilder method(HttpMethod method, String uri, Object body) {
        this.httpMethod = method;
        this.uri = uri;
        this.body = body;
        return this;
    }

    public <T> Action<T> response(Class<T> type) {
        return build(ParameterizedTypeReference.forType(type));
    }

    public <T> Action<T> response(ParameterizedTypeReference<T> type) {
        return build(type);
    }

    public <T> Action<T> spec(HttpRequestSpec<T> spec) {
        return new GenericHttpAction<>(httpExecutor, spec);
    }

    private <T> Action<T> build(ParameterizedTypeReference<T> type) {
        Objects.requireNonNull(httpMethod, "HTTP method cannot be null");
        Objects.requireNonNull(type, "type cannot be null");
        if (uri == null || uri.isBlank()) throw new IllegalArgumentException("URI cannot be null or blank");

        HttpRequestSpec<T> spec = new HttpRequestSpec<>(
                httpMethod,
                uri,
                body,
                type
        );

        return new GenericHttpAction<>(httpExecutor, spec);
    }

}
