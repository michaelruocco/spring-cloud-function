package uk.co.mruoc.function;

import org.springframework.http.HttpStatus;
import uk.co.mruoc.AbstractException;

public class InvalidJsonException extends AbstractException {

    private static final int STATUS = HttpStatus.BAD_REQUEST.value();
    private static final String CODE = "INVALID_JSON";
    private static final String TITLE = "json is invalid";

    public InvalidJsonException(Throwable cause, String json) {
        super(cause, STATUS, CODE, TITLE, buildDetail(cause, json));
    }

    private static String buildDetail(Throwable cause, String json) {
        return String.format("json [%s] is invalid: %s", json, cause.getMessage());
    }

}
