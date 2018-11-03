package uk.co.mruoc;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

public class HeadersBuilder {

    private final Map<String, String> headers = new HashMap<>();

    public HeadersBuilder withLocation(String uri) {
        headers.put("Location", uri);
        return this;
    }

    public HeadersBuilder withHost(String host) {
        headers.put("Host", host);
        return this;
    }

    public Map<String, String> build() {
        return unmodifiableMap(headers);
    }
}
