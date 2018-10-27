package uk.co.mruoc;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

import static java.util.Collections.emptyMap;

@Getter
@Builder
@ToString
public class BasicResponse<O> implements Response<O> {

    private final O body;
    private final int statusCode;
    @Builder.Default private final Map<String, String> headers = emptyMap();

}
