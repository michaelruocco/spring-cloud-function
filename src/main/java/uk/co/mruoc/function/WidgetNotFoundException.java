package uk.co.mruoc.function;

import uk.co.mruoc.AbstractException;

public class WidgetNotFoundException extends AbstractException {

    public WidgetNotFoundException(long id) {
        super(404, String.format("no widgets found with id [%s]", id), "WIDGET_NOT_FOUND");
    }

}
