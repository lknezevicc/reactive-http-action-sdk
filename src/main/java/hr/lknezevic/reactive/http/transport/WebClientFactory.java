package hr.lknezevic.reactive.http.transport;

import hr.lknezevic.reactive.http.config.HttpClientConfig;
import hr.lknezevic.reactive.http.transport.filter.DefaultFilters;
import lombok.experimental.UtilityClass;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@UtilityClass
public class WebClientFactory {

    public WebClient create(HttpClientConfig httpClientConfig, List<ExchangeFilterFunction> filters) {
        return WebClient.builder()
                .baseUrl(httpClientConfig.baseUrl())
                .defaultHeaders(headers -> httpClientConfig.headers().forEach(headers::set))
                .clientConnector(NettyClientFactory.connector(httpClientConfig))
                .exchangeStrategies(exchangeStrategies(httpClientConfig))
                .filters(fs -> fs.addAll(filters))
                .build();
    }

    public WebClient createWithDefaultFilters(HttpClientConfig httpClientConfig) {
        List<ExchangeFilterFunction> filters = List.of(
                DefaultFilters.logging(),
                DefaultFilters.errorHandling(),
                DefaultFilters.retry(httpClientConfig));

        return create(httpClientConfig, filters);
    }

    ExchangeStrategies exchangeStrategies(HttpClientConfig httpClientConfig) {
        return ExchangeStrategies.builder()
                .codecs(
                        codecConfigurer ->
                                codecConfigurer
                                        .defaultCodecs()
                                        .maxInMemorySize(httpClientConfig.maxInMemorySizeBytes())
                )
                .build();
    }
}
