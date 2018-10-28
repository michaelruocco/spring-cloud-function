package uk.co.mruoc.function;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import uk.co.mruoc.model.FakeWidget;
import uk.co.mruoc.model.Widget;
import uk.co.mruoc.repository.WidgetRepository;

import java.util.Optional;
import java.util.UUID;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class DefaultWidgetServiceTest {

    private static final UUID ID = UUID.randomUUID();

    private final WidgetRepository repository = mock(WidgetRepository.class);

    private final WidgetService service = new DefaultWidgetService();

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(service, "repository", repository);
    }

    @Test
    public void shouldReturnWidget() {
        Optional<Widget> expectedWidget = Optional.of(new FakeWidget());
        given(repository.findById(ID)).willReturn(expectedWidget);

        Optional<Widget> widget = service.getWidget(ID);

        assertThat(widget).isEqualTo(expectedWidget);
    }

    @Test
    public void shouldReturnEmptyOptionalIfWidgetNotFound() {
        Optional<Widget> expectedWidget = Optional.empty();
        given(repository.findById(ID)).willReturn(expectedWidget);

        Optional<Widget> widget = service.getWidget(ID);

        assertThat(widget).isEqualTo(expectedWidget);
    }

    @Test
    public void shouldReturnFalseIfIdIsNull() {
        boolean exists = service.exists(null);

        assertThat(exists).isFalse();
    }

    @Test
    public void shouldReturnFalseIfIdNotFound() {
        given(repository.existsById(ID)).willReturn(false);

        boolean exists = service.exists(ID);

        assertThat(exists).isFalse();
    }

    @Test
    public void shouldReturnTrueIfIdNotFound() {
        given(repository.existsById(ID)).willReturn(true);

        boolean exists = service.exists(ID);

        assertThat(exists).isTrue();
    }

    @Test
    public void shouldCreateWidget() {
        Widget savedWidget = new FakeWidget();
        Widget expectedWidget = new FakeWidget();
        given(repository.save(savedWidget)).willReturn(expectedWidget);

        Widget widget = service.createWidget(savedWidget);

        assertThat(widget).isEqualTo(expectedWidget);
    }

    @Test
    public void shouldReturnNoWidgetsIfNoneFound() {
        given(repository.findAll()).willReturn(emptyList());

        Iterable<Widget> widgets = service.getAllWidgets();

        assertThat(widgets).isEmpty();
    }

    @Test
    public void shouldReturnAllWidgets() {
        Widget widget1 = new FakeWidget();
        Widget widget2 = new FakeWidget();
        given(repository.findAll()).willReturn(asList(widget1, widget2));

        Iterable<Widget> widgets = service.getAllWidgets();

        assertThat(widgets).containsExactly(widget1, widget2);
    }

}
