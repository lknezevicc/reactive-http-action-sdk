package hr.lknezevic.reactive.http.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class UriQueryParameterBuilder {
    private final String baseUri;
    private final Map<String, Object> parameters;

    public UriQueryParameterBuilder(String baseUri) {
        this.baseUri = baseUri;
        this.parameters = new LinkedHashMap<>();
    }

    public UriQueryParameterBuilder withParam(String key, Object value) {
        if (value == null) return this;

        if (value instanceof String s && s.isBlank()) return this;

        parameters.put(key, value);
        return this;
    }

    public String build() {
        if (parameters.isEmpty()) {
            return baseUri;
        }

        String query = parameters.entrySet().stream()
                .map(entry ->
                        String.format("%s=%s",
                                URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8),
                                URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8))
                ).collect(Collectors.joining("&"));

        return baseUri + "?" + query;
    }
}
