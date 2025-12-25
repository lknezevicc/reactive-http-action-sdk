package hr.lknezevic.reactive.http.action;

import reactor.core.publisher.Mono;

public sealed interface Action<T> permits AbstractHttpAction {
    Mono<T> execute();
}