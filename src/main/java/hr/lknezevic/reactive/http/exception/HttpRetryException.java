package hr.lknezevic.reactive.http.exception;

import java.net.URI;

public class HttpRetryException extends RuntimeException {
    public HttpRetryException(URI uri, Throwable cause) {
        super("Retries exhausted for " + uri, cause);
    }
}
