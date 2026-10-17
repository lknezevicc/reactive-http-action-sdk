# reactive-http-action-sdk

A small Java library for describing HTTP calls as reusable, typed actions on top of Spring WebFlux. An action is a request (method, path, body, response type) that you build once and execute as a `Mono`, a `CompletableFuture` or a callback.

## Features

- Fluent builder with compile-time steps: pick the method, add a payload when the method needs one, then pick the response type.
- Typed responses through `Class<T>` or `ParameterizedTypeReference<T>`.
- One `HttpExecutor` interface between actions and the transport, with a `WebClient` implementation included.
- Netty-based client with configurable base URL, default headers, connect/read/write timeouts and in-memory buffer size.
- Retries with exponential backoff and jitter. Retries apply to idempotent methods (GET, HEAD, OPTIONS) on network errors, 5xx responses and configurable status codes (502, 503, 504 by default). Exhausted retries end in an `HttpRetryException`.
- Default filters for logging, error handling and retry.

## Usage

```java
HttpClientConfig config = HttpClientConfig.withDefaults("https://api.example.com")
        .withHeader("Authorization", "Bearer " + token);

WebClient webClient = WebClientFactory.createWithDefaultFilters(config);
HttpExecutor executor = new WebClientHttpExecutor(webClient);

// GET
HttpAction<User> getUser = HttpActions.builder(executor)
        .get("/users/42")
        .response(User.class);

Mono<User> user = getUser.execute();
CompletableFuture<User> future = getUser.toFuture();
getUser.executeAndSubscribe(u -> System.out.println(u.name()));

// POST with a body
HttpAction<User> createUser = HttpActions.builder(executor)
        .post("/users")
        .payload(new NewUser("Ada"))
        .response(User.class);

// Or build the request spec yourself
HttpAction<User> fromSpec = HttpActions.fromSpec(executor,
        HttpRequestSpec.get("/users/42", User.class));
```

Defaults when not set: connect timeout 30 s, read and write timeout 5 s, 16 MB max in-memory size, 3 retries with a 3 s backoff.

## Build

Requires Java 21.

```bash
./mvnw clean install
```

## Status

Early stage. The API can still change and there are no tests yet. `HEAD`, `OPTIONS` and `TRACE` requests are not supported by the `WebClient` executor.

## Tech stack

Java 21, Spring WebFlux 7, Reactor Netty, Project Reactor, SLF4J, Lombok, Maven.
