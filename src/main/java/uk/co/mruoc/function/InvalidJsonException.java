package uk.co.mruoc.function;

import uk.co.mruoc.AbstractException;

public class InvalidJsonException extends AbstractException {

    public InvalidJsonException(Throwable cause, String json) {
        super(cause, 400, String.format("invalid json [%s]", json), "INVALID_JSON");
    }

}
