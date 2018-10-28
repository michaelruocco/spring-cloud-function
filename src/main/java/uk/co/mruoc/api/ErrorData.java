package uk.co.mruoc.api;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@Getter
@Builder
public class ErrorData {

    private final UUID id;
    private final String code;
    private final String title;
    private final String detail;
    private final Map<String, Object> meta;

}