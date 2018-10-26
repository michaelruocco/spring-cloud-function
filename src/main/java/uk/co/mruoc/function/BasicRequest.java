package uk.co.mruoc.function;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@Builder
@ToString
public class BasicRequest<I> implements Request<I> {

    private final I body;
    private final Map<String, String> headers;
    private final Map<String, String> pathParameters;
    private final Map<String, String> queryStringParameters;

}
