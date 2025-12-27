package hr.lknezevic.reactive.http.action.builder;

import hr.lknezevic.reactive.http.action.HttpAction;
import hr.lknezevic.reactive.http.action.HttpRequestSpec;
import hr.lknezevic.reactive.http.transport.HttpExecutor;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HttpActions {

    public HttpMethodStep builder(HttpExecutor httpExecutor) {
        return new HttpActionBuilder(httpExecutor);
    }

    public <T> HttpAction<T> fromSpec(HttpExecutor httpExecutor, HttpRequestSpec<T> requestSpec) {
        return new GenericHttpAction<>(httpExecutor, requestSpec);
    }
}
