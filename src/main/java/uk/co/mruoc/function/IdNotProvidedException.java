package uk.co.mruoc.function;

import org.springframework.http.HttpStatus;
import uk.co.mruoc.AbstractException;

public class IdNotProvidedException extends AbstractException {

    private static final int STATUS = HttpStatus.BAD_REQUEST.value();
    private static final String CODE = "WIDGET_ID_NOT_PROVIDED";
    private static final String TITLE = "widget id must be provided";

    public IdNotProvidedException() {
        super(STATUS, CODE, TITLE, TITLE);
    }

}
