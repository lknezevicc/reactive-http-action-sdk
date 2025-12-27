package hr.lknezevic.reactive.http.action.builder;

public sealed interface HttpPayloadStep permits HttpActionBuilder {
    HttpResponseStep payload(Object payload);
}
