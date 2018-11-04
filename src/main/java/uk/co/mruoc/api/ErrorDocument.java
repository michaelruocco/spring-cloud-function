package uk.co.mruoc.api;

import lombok.Getter;

import java.util.List;

import static java.util.Arrays.asList;

@Getter
public class ErrorDocument {

    private final List<ErrorData> errors;

    public ErrorDocument(ErrorData... error) {
        this(asList(error));
    }

    public ErrorDocument(List<ErrorData> errors) {
        this.errors = errors;
    }

}
