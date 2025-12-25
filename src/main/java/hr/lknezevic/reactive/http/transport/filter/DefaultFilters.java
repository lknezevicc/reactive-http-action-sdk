package hr.lknezevic.reactive.http.transport.filter;

import hr.lknezevic.reactive.http.config.HttpClientConfig;
import lombok.experimental.UtilityClass;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

@UtilityClass
public class DefaultFilters {

    public ExchangeFilterFunction logging() {
        return LoggingFilter.debugLogging();
    }

    public ExchangeFilterFunction retry(HttpClientConfig httpClientConfig) {
        return RetryFilter.retry(httpClientConfig);
    }

    public ExchangeFilterFunction errorHandling() {
        return ErrorHandlingFilter.errorHandling();
    }
}
