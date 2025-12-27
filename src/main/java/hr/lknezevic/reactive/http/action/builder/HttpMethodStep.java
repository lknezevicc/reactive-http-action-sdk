package hr.lknezevic.reactive.http.action.builder;

public sealed interface HttpMethodStep permits HttpActionBuilder {
    HttpResponseStep get(String path);
    HttpResponseStep delete(String path);
    HttpPayloadStep post(String path);
    HttpPayloadStep put(String path);
    HttpPayloadStep patch(String path);
}
