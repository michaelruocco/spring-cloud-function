package uk.co.mruoc.function;

import uk.co.mruoc.AbstractException;

import static org.springframework.http.HttpStatus.*;

public class IdNotProvidedException extends AbstractException {

    private static final int STATUS = BAD_REQUEST.value();
    private static final String CODE = "WIDGET_ID_NOT_PROVIDED";
    private static final String TITLE = "widget id must be provided";

    public IdNotProvidedException() {
        super(STATUS, CODE, TITLE, TITLE);
    }

}
