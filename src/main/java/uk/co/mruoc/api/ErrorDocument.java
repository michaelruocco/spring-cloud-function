package uk.co.mruoc.api;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class ErrorDocument {

    private final String title;
    private final String code;
    private final Map<String, Object> meta;

}
