package hr.lknezevic.reactive.http.config;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;

public record HttpClientConfig(
        String baseUrl,
        Map<String, String> headers,
        Duration connectTimeout,
        Duration readTimeout,
        Duration writeTimeout,
        int maxInMemorySizeBytes,
        RetryConfig retry
) {

    public HttpClientConfig {
        Objects.requireNonNull(baseUrl, "baseUrl must not be null");

        headers = headers != null ? Map.copyOf(headers) : Map.of();
        connectTimeout = connectTimeout != null ? connectTimeout : Duration.ofSeconds(30);
        readTimeout = readTimeout != null ? readTimeout : Duration.ofSeconds(5);
        writeTimeout = writeTimeout != null ? writeTimeout : Duration.ofSeconds(5);
        maxInMemorySizeBytes = maxInMemorySizeBytes > 0
                ? maxInMemorySizeBytes
                : 16 * 1024 * 1024;
        retry = retry != null ? retry : RetryConfig.defaults();
    }

    public static HttpClientConfig withDefaults(String baseUrl) {
        return new HttpClientConfig(baseUrl,
                null,
                null,
                null,
                null,
                0,
                RetryConfig.defaults());
    }
}
