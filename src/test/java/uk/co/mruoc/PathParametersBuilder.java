package uk.co.mruoc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.util.Collections.unmodifiableMap;

public class PathParametersBuilder {

    private final Map<String, String> params = new HashMap<>();

    public PathParametersBuilder withId(UUID id) {
        params.put("id", id.toString());
        return this;
    }

    public Map<String, String> build() {
        return unmodifiableMap(params);
    }

}
