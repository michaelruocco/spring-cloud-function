package uk.co.mruoc;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@Builder
@ToString
public class DefaultResponse<O> implements Response<O> {

    private final O body;
    private final int statusCode;
    private final Map<String, String> headers;

}
