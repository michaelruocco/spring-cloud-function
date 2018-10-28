package uk.co.mruoc.api;

import lombok.Getter;

import java.util.List;

import static java.util.Collections.singletonList;

@Getter
public class ErrorDocument {

    private final List<ErrorData> errors;

    public ErrorDocument(ErrorData error) {
        this(singletonList(error));
    }

    public ErrorDocument(List<ErrorData> errors) {
        this.errors = errors;
    }

}
