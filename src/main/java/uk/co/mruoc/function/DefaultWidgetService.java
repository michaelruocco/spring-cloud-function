package uk.co.mruoc.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        log.info("saving widget {}", widget);
        return repository.save(widget);
    }

    @Override
    public Page<Widget> getWidgets(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public void deleteWidget(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public Widget updateWidget(UUID id, Widget widget) {
        final Optional<Widget> originalWidget = getWidget(id);
        if (originalWidget.isPresent()) {
            final Widget updatedWidget = originalWidget.get().update(widget);
            return repository.save(updatedWidget);
        }
        throw new WidgetNotFoundException(id);
    }

}
