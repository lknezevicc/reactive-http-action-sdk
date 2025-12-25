package hr.lknezevic.reactive.http.config;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public record RetryConfig(
        int times,
        Duration backoff,
        boolean retryOnServerError,
        List<Integer> retryStatusCodes
) {

    public RetryConfig {
        Objects.requireNonNull(backoff, "backoff must not be null");
        times = times <= 0 ? 3 : times;
        retryStatusCodes = retryStatusCodes != null ? List.copyOf(retryStatusCodes) : List.of(502, 503, 504);
    }

    public static RetryConfig defaults() {
        return new RetryConfig(3, Duration.ofSeconds(3), true, List.of(502, 503, 504));
    }
}
