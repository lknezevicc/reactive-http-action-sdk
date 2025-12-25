package hr.lknezevic.reactive.http.transport.filter;

import lombok.experimental.UtilityClass;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

@UtilityClass
class ErrorHandlingFilter {

    ExchangeFilterFunction errorHandling() {
        return ExchangeFilterFunction.ofResponseProcessor(response -> {
            if (!response.statusCode().isError()) {
                return Mono.just(response);
            }

            return response.createException().flatMap(Mono::error);
        });
    }
}
