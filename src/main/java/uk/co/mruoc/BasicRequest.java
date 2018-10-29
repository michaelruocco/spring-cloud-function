package uk.co.mruoc;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Builder
@Getter
@ToString
public class BasicRequest<I> implements Request<I> {

    private final I body;
    private final String uri;
    private final Map<String, String> headers;
    private final Map<String, String> pathParameters;
    private final Map<String, String> queryStringParameters;

    @Override
    public boolean hasHeader(String name) {
        return headers != null && headers.containsKey(name);
    }

    @Override
    public String getHeader(String name) {
        return headers.get(name);
    }

    @Override
    public boolean hasPathParameter(String name) {
        return pathParameters != null && pathParameters.containsKey(name);
    }

    @Override
    public String getPathParameter(String name) {
        return pathParameters.get(name);
    }

    @Override
    public boolean hasQueryStringParameter(String name) {
        return queryStringParameters != null && queryStringParameters.containsKey(name);
    }

    @Override
    public String getQueryStringParameter(String name) {
        return queryStringParameters.get(name);
    }

}
