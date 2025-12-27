package hr.lknezevic.reactive.http.action;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public sealed interface HttpAction<T> permits AbstractHttpAction {
    Mono<T> execute();

    default void executeAndSubscribe(Consumer<T> consumer) {
        execute().subscribe(consumer);
    }

    default void executeAndSubscribeOn(Consumer<T> consumer, Scheduler scheduler) {
        execute().subscribeOn(scheduler).subscribe(consumer);
    }

    default CompletableFuture<T> toFuture() {
        return execute().toFuture();
    }
}