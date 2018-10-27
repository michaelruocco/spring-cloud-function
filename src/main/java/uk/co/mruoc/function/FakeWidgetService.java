package uk.co.mruoc.function;

import lombok.extern.slf4j.Slf4j;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Component;
import uk.co.mruoc.api.WidgetDocument;

import java.util.Optional;

@Slf4j
@Component
public class FakeWidgetService implements WidgetService {

    @Override
    public Optional<WidgetDocument> getWidget(long id) {
        log.info("getting widget with id {}", id);
        if (shouldSucceed(id)) {
            return Optional.of(buildDocument(id));
        }
        log.info("no widgets found with id {}", id);
        return Optional.empty();
    }

    @Override
    public Optional<WidgetDocument> createWidget(WidgetDocument widget) {
        long id = widget.getId();
        if (shouldSucceed(id)) {
            return Optional.of(widget);
        }
        log.info("widget already exists with id {}", id);
        throw new WidgetAlreadyExistsException(id);
    }

    private boolean shouldSucceed(long id) {
        return !Long.toString(id).endsWith("3");
    }

    private static WidgetDocument buildDocument(long id) {
        WidgetDocument widget = new WidgetDocument();
        widget.setId(id);
        widget.setDescription("widget " + id);
        widget.setCost(Money.of(id + 10, "GBP"));
        widget.setPrice(Money.of(id + 12, "GBP"));
        return widget;
    }

}
