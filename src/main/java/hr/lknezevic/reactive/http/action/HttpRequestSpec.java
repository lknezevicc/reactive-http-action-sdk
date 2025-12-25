package hr.lknezevic.reactive.http.action;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.Objects;

public record HttpRequestSpec<T>(
        HttpMethod httpMethod,
        String uri,
        Object body,
        ParameterizedTypeReference<T> responseType
) {

    public static <T> HttpRequestSpec<T> get(String uri, Class<T> responseType) {
        return build(HttpMethod.GET, uri, null, ParameterizedTypeReference.forType(responseType));
    }

    public static <T> HttpRequestSpec<T> get(String uri, ParameterizedTypeReference<T> responseType) {
        return build(HttpMethod.GET, uri, null, responseType);
    }

    public static <T> HttpRequestSpec<T> delete(String uri, Class<T> responseType) {
        return build(HttpMethod.DELETE, uri, null, ParameterizedTypeReference.forType(responseType));
    }

    public static <T> HttpRequestSpec<T> delete(String uri, ParameterizedTypeReference<T> responseType) {
        return build(HttpMethod.DELETE, uri, null, responseType);
    }

    public static <T> HttpRequestSpec<T> post(String uri, Object body, Class<T> responseType) {
        return build(HttpMethod.POST, uri, body, ParameterizedTypeReference.forType(responseType));
    }

    public static <T> HttpRequestSpec<T> post(String uri, Object body, ParameterizedTypeReference<T> responseType) {
        return build(HttpMethod.POST, uri, body, responseType);
    }

    public static <T> HttpRequestSpec<T> put(String uri, Object body, Class<T> responseType) {
        return build(HttpMethod.PUT, uri, body, ParameterizedTypeReference.forType(responseType));
    }

    public static <T> HttpRequestSpec<T> put(String uri, Object body, ParameterizedTypeReference<T> responseType) {
        return build(HttpMethod.PUT, uri, body, responseType);
    }

    public static <T> HttpRequestSpec<T> patch(String uri, Object body, Class<T> responseType) {
        return build(HttpMethod.PATCH, uri, body, ParameterizedTypeReference.forType(responseType));
    }

    public static <T> HttpRequestSpec<T> patch(String uri, Object body, ParameterizedTypeReference<T> responseType) {
        return build(HttpMethod.PATCH, uri, body, responseType);
    }

    private static <T> HttpRequestSpec<T> build(HttpMethod method, String uri, Object body, ParameterizedTypeReference<T> responseType) {
        Objects.requireNonNull(method, "HTTP method cannot be null");
        Objects.requireNonNull(responseType, "type cannot be null");
        if (uri == null || uri.isBlank()) throw new IllegalArgumentException("URI cannot be null or blank");

        return new HttpRequestSpec<>(method, uri, body, responseType);
    }

}
