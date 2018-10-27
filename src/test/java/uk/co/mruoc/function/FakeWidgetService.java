package uk.co.mruoc.function;

import lombok.extern.slf4j.Slf4j;
import org.javamoney.moneta.Money;
import uk.co.mruoc.model.Widget;

import java.util.Optional;

@Slf4j
public class FakeWidgetService implements WidgetService {

    @Override
    public Optional<Widget> getWidget(long id) {
        log.info("getting widget with id {}", id);
        if (shouldSucceed(id)) {
            return Optional.of(buildModel(id));
        }
        log.info("no widgets found with id {}", id);
        return Optional.empty();
    }

    @Override
    public Widget createWidget(Widget widget) {
        long id = widget.getId();
        if (shouldSucceed(id)) {
            return widget;
        }
        log.info("widget already exists with id {}", id);
        throw new WidgetAlreadyExistsException(id);
    }

    private boolean shouldSucceed(long id) {
        return !Long.toString(id).endsWith("3");
    }

    private static Widget buildModel(long id) {
        Widget widget = new Widget();
        widget.setId(id);
        widget.setDescription("widget " + id);
        widget.setCost(Money.of(id + 10, "GBP"));
        widget.setPrice(Money.of(id + 12, "GBP"));
        return widget;
    }

}
