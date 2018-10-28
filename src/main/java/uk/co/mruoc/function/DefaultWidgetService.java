package uk.co.mruoc.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.mruoc.model.Widget;
import uk.co.mruoc.repository.WidgetRepository;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class DefaultWidgetService implements WidgetService {

    @Autowired
    private WidgetRepository repository;

    @Override
    public Optional<Widget> getWidget(UUID id) {
        log.info("getting widget with id {}", id);
        return repository.findById(id);
    }

    @Override
    public boolean exists(UUID id) {
        return id != null && repository.existsById(id);
    }

    @Override
    public Widget createWidget(Widget widget) {
        return repository.save(widget);
    }

}
