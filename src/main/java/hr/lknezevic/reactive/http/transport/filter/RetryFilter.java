package hr.lknezevic.reactive.http.transport.filter;

import hr.lknezevic.reactive.http.config.HttpClientConfig;
import hr.lknezevic.reactive.http.config.RetryConfig;
import hr.lknezevic.reactive.http.config.RetryPolicy;
import lombok.experimental.UtilityClass;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

@UtilityClass
class RetryFilter {

    ExchangeFilterFunction retry(HttpClientConfig httpClientConfig) {
        return ((request, next) -> {
            Mono<ClientResponse> response = next.exchange(request)
                    .flatMap(resp -> {
                        int status = resp.statusCode().value();
                        RetryConfig rc = httpClientConfig.retry();

                        boolean statusShouldRetry = (rc.retryOnServerError() && resp.statusCode().is5xxServerError())
                                || (rc.retryStatusCodes() != null && rc.retryStatusCodes().contains(status));

                        if (statusShouldRetry) {
                            return resp.createError();
                        }

                        return Mono.just(resp);
                    });

            if (RetryPolicy.isIdempotent(request.method())) {
                response = response.retryWhen(RetryPolicy.create(httpClientConfig.retry(), request.url()));
            }

            return response;
        });
    }
}
