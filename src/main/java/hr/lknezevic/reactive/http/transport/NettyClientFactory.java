package hr.lknezevic.reactive.http.transport;

import hr.lknezevic.reactive.http.config.HttpClientConfig;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.experimental.UtilityClass;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import reactor.netty.http.client.HttpClient;

import java.util.concurrent.TimeUnit;

@UtilityClass
class NettyClientFactory {

    ReactorClientHttpConnector connector(HttpClientConfig httpClientConfig) {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int) httpClientConfig.connectTimeout().toMillis())
                .secure()
                .responseTimeout(httpClientConfig.readTimeout())
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(
                                httpClientConfig.readTimeout().toMillis(),
                                TimeUnit.MILLISECONDS))
                        .addHandlerLast(new WriteTimeoutHandler(
                                httpClientConfig.writeTimeout().toMillis(),
                                TimeUnit.MILLISECONDS))
                );

        return new ReactorClientHttpConnector(httpClient);
    }
}
