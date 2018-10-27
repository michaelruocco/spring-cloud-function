package uk.co.mruoc.function;

import uk.co.mruoc.AbstractException;

public class WidgetAlreadyExistsException extends AbstractException {

    public WidgetAlreadyExistsException(long id) {
        super(422, String.format("a widget already exists with id [%s]", id), "WIDGET_ALREADY_EXISTS");
    }

}
