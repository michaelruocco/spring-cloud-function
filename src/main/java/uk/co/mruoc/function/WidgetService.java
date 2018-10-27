package uk.co.mruoc.function;

import uk.co.mruoc.model.Widget;

import java.util.Optional;

public interface WidgetService {

    Optional<Widget> getWidget(long id);

    Widget createWidget(Widget widget);

}
