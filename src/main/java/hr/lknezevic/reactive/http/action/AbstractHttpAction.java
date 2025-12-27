package hr.lknezevic.reactive.http.action;

import hr.lknezevic.reactive.http.transport.HttpExecutor;
import reactor.core.publisher.Mono;

public non-sealed abstract class AbstractHttpAction<T> implements HttpAction<T> {
    protected final HttpExecutor httpExecutor;

    protected AbstractHttpAction(HttpExecutor httpExecutor) {
        this.httpExecutor = httpExecutor;
    }

    protected abstract HttpRequestSpec<T> getRequestSpec();

    @Override
    public Mono<T> execute() {
        return httpExecutor.execute(getRequestSpec());
    }
}
