package hr.lknezevic.reactive.http.action;

import hr.lknezevic.reactive.http.transport.HttpExecutor;

final class GenericHttpAction<T> extends AbstractHttpAction<T> {
    private final HttpRequestSpec<T> requestSpec;

    public GenericHttpAction(HttpExecutor httpExecutor, HttpRequestSpec<T> requestSpec) {
        super(httpExecutor);
        this.requestSpec = requestSpec;
    }

    @Override
    protected HttpRequestSpec<T> getRequestSpec() {
        return requestSpec;
    }
}
