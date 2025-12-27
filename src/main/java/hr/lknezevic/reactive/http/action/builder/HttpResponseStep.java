package hr.lknezevic.reactive.http.action.builder;

import hr.lknezevic.reactive.http.action.HttpAction;
import org.springframework.core.ParameterizedTypeReference;

public sealed interface HttpResponseStep permits HttpActionBuilder {
    <T> HttpAction<T> response(Class<T> type);
    <T> HttpAction<T> response(ParameterizedTypeReference<T> type);
}
