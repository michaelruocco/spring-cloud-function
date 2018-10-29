package uk.co.mruoc.function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uk.co.mruoc.model.Widget;

import java.util.Optional;
import java.util.UUID;

public interface WidgetService {

    Optional<Widget> getWidget(UUID id);

    boolean exists(UUID id);

    Widget createWidget(Widget widget);

    Page<Widget> getWidgets(Pageable pageable);

}
