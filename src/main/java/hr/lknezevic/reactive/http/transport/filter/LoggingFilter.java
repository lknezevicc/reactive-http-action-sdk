package hr.lknezevic.reactive.http.transport.filter;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

@UtilityClass
@Slf4j(topic = "WebClientLoggingFilter")
class LoggingFilter {

    ExchangeFilterFunction debugLogging() {
        return ((request, next) -> {
            log.debug("[REQUEST] {} {}", request.method(), request.url());

            return next.exchange(request)
                    .doOnNext(response ->
                            log.debug("[RESPONSE] {} {} -> {}",
                                    request.method(),
                                    request.url(),
                                    response.statusCode()
                    ));
        });
    }
}
