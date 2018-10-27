package uk.co.mruoc.function;

import uk.co.mruoc.AbstractException;

public class IdNotProvidedException extends AbstractException {

    public IdNotProvidedException() {
        super(400, "widget id must be provided", "WIDGET_ID_NOT_PROVIDED");
    }

}
