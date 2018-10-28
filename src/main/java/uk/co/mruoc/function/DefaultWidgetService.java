package uk.co.mruoc.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.mruoc.UnexpectedErrorException;
import uk.co.mruoc.model.Widget;
import uk.co.mruoc.repository.WidgetRepository;

import java.util.Optional;

@Slf4j
@Component
public class DefaultWidgetService implements WidgetService {

    @Autowired
    private WidgetRepository repository;

    @Override
    public Optional<Widget> getWidget(long id) {
        log.info("getting widget with id {}", id);
        return repository.findById(id);
    }

    @Override
    public boolean exists(long id) {
        return repository.existsById(id);
    }

    @Override
    public Widget createWidget(Widget widget) {
        Long id = widget.getId();
        repository.save(widget);
        Optional<Widget> created = repository.findById(id);
        return created.orElseThrow(() -> new UnexpectedErrorException("create widget failed"));
    }

}
