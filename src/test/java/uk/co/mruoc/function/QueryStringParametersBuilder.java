package uk.co.mruoc.function;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

public class QueryStringParametersBuilder {

    private final Map<String, String> params = new HashMap<>();

    public QueryStringParametersBuilder withPageNumber(int pageNumber) {
        params.put("pageNumber", Integer.toString(pageNumber));
        return this;
    }

    public QueryStringParametersBuilder withPageSize(int pageSize) {
        params.put("pageSize", Integer.toString(pageSize));
        return this;
    }

    public Map<String, String> build() {
        return unmodifiableMap(params);
    }

}
