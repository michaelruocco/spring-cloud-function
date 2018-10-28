package uk.co.mruoc.function;

import uk.co.mruoc.model.Widget;

import java.util.Optional;
import java.util.UUID;

public interface WidgetService {

    Optional<Widget> getWidget(UUID id);

    boolean exists(UUID id);

    Widget createWidget(Widget widget);

    Iterable<Widget> getAllWidgets();

}
