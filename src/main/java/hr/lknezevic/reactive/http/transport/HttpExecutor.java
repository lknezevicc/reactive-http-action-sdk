package hr.lknezevic.reactive.http.transport;

import hr.lknezevic.reactive.http.action.HttpRequestSpec;
import reactor.core.publisher.Mono;

public interface HttpExecutor {
    <T> Mono<T> execute(HttpRequestSpec<T> requestSpec);
}
