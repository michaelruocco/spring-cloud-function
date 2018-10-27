package uk.co.mruoc.function;

import uk.co.mruoc.api.WidgetDocument;

import java.util.Optional;

public interface WidgetService {
    Optional<WidgetDocument> getWidget(long id);

    Optional<WidgetDocument> createWidget(WidgetDocument widget);
}
