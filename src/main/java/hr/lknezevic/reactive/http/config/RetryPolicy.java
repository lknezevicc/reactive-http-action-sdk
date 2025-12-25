package hr.lknezevic.reactive.http.config;

import hr.lknezevic.reactive.http.exception.HttpRetryException;
import io.netty.handler.timeout.ReadTimeoutException;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.Exceptions;
import reactor.util.retry.Retry;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

@UtilityClass
public class RetryPolicy {

    public Retry create(RetryConfig config, URI uri) {
        Objects.requireNonNull(config, "retry config must not be null");

        return Retry.backoff(config.times(), config.backoff())
            .jitter(0.5)
            .filter(t -> isRetryable(t, config))
            .onRetryExhaustedThrow((spec, rs) ->
                new HttpRetryException(uri, rs.failure())
            );
    }

    public boolean isIdempotent(HttpMethod method) {
        return method == HttpMethod.GET
                || method == HttpMethod.HEAD
                || method == HttpMethod.OPTIONS;
    }

    public boolean isRetryable(Throwable t) {
        return isRetryable(t, RetryConfig.defaults());
    }

    public boolean isRetryable(Throwable t, RetryConfig config) {
        Throwable root = Exceptions.unwrap(t);

        boolean network = (root instanceof ConnectException)
                || (root instanceof SocketTimeoutException)
                || (root instanceof TimeoutException)
                || (root instanceof IOException)
                || (root instanceof ReadTimeoutException);

        if (network) return true;

        if (root instanceof WebClientResponseException wre) {
            int status = wre.getStatusCode().value();

            if (config.retryOnServerError() && status >= 500 && status < 600) {
                return true;
            }

            List<Integer> codes = config.retryStatusCodes();
            return codes != null && !codes.isEmpty() && codes.contains(status);
        }

        return false;
    }
}
