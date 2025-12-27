package hr.lknezevic.reactive.http.action.builder;

import hr.lknezevic.reactive.http.action.HttpAction;
import hr.lknezevic.reactive.http.action.HttpRequestSpec;
import hr.lknezevic.reactive.http.transport.HttpExecutor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.Objects;

final class HttpActionBuilder implements HttpMethodStep, HttpPayloadStep, HttpResponseStep {
    private final HttpExecutor httpExecutor;
    private final HttpMethod httpMethod;
    private final String path;
    private final Object payload;

    HttpActionBuilder(HttpExecutor httpExecutor) {
        this(httpExecutor, null, null, null);
    }

    HttpActionBuilder(HttpExecutor httpExecutor, HttpMethod httpMethod, String path, Object payload) {
        this.httpExecutor = Objects.requireNonNull(httpExecutor);
        this.httpMethod = httpMethod;
        this.path = path;
        this.payload = payload;
    }

    @Override
    public HttpResponseStep get(String path) {
        return new HttpActionBuilder(httpExecutor, HttpMethod.GET, path, this.payload);
    }

    @Override
    public HttpResponseStep delete(String path) {
        return new HttpActionBuilder(httpExecutor, HttpMethod.DELETE, path, this.payload);
    }

    @Override
    public HttpPayloadStep post(String path) {
        return new HttpActionBuilder(httpExecutor, HttpMethod.POST, path, this.payload);
    }

    @Override
    public HttpPayloadStep put(String path) {
        return new HttpActionBuilder(httpExecutor, HttpMethod.PUT, path, this.payload);
    }

    @Override
    public HttpPayloadStep patch(String path) {
        return new HttpActionBuilder(httpExecutor, HttpMethod.PATCH, path, this.payload);
    }

    @Override
    public HttpResponseStep payload(Object payload) {
        return new HttpActionBuilder(httpExecutor, this.httpMethod, this.path, payload);
    }

    @Override
    public <T> HttpAction<T> response(Class<T> type) {
        return response(ParameterizedTypeReference.forType(type));
    }

    @Override
    public <T> HttpAction<T> response(ParameterizedTypeReference<T> type) {
        Objects.requireNonNull(httpMethod, "HTTP method cannot be null");
        Objects.requireNonNull(type, "type cannot be null");
        if (path == null || path.isBlank()) throw new IllegalArgumentException("URI cannot be null or blank");

        HttpRequestSpec<T> spec = new HttpRequestSpec<>(
                httpMethod,
                path,
                payload,
                type
        );

        return new GenericHttpAction<>(httpExecutor, spec);
    }
}
