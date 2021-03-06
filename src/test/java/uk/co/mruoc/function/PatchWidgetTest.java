package uk.co.mruoc.function;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent.ProxyRequestContext;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import uk.co.mruoc.JacksonConfiguration;
import uk.co.mruoc.PathParametersBuilder;
import uk.co.mruoc.model.FakeWidget;

import java.util.UUID;

import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

public class PatchWidgetTest {

    private final ObjectMapper mapper = JacksonConfiguration.getMapper();
    private final WidgetService service = mock(WidgetService.class);
    private final FakeWidget widget = new FakeWidget();
    private final PathParametersBuilder pathParametersBuilder = new PathParametersBuilder();

    private final PatchWidget patchWidget = new PatchWidget();

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(patchWidget, "mapper", mapper);
        ReflectionTestUtils.setField(patchWidget, "service", service);
    }

    @Test
    public void shouldUpdateWidget() {
        given(service.updateWidget(widget.getId(), widget)).willReturn(widget);
        final String body = widget.asJsonDocument();
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody(body)
                .withPathParamters(pathParametersBuilder.withId(widget.getId()).build())
                .withHeaders(emptyMap())
                .withRequestContext(new ProxyRequestContext());


        final APIGatewayProxyResponseEvent response = patchWidget.apply(request);

        assertThat(response.getStatusCode()).isEqualTo(OK.value());
        assertThat(response.getBody()).isEqualTo(body);
        assertThat(response.getHeaders()).isEmpty();
    }

    @Test
    public void shouldReturnErrorIfWidgetNotFound() {
        given(service.updateWidget(widget.getId(), widget)).willThrow(new WidgetNotFoundException(widget.getId()));
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody(widget.asJsonDocument())
                .withPathParamters(pathParametersBuilder.withId(widget.getId()).build())
                .withHeaders(emptyMap())
                .withRequestContext(new ProxyRequestContext());

        final APIGatewayProxyResponseEvent response = patchWidget.apply(request);

        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND.value());
        assertThat(response.getBody()).startsWith("{\"errors\":[{\"id\":\"");
        assertThat(response.getBody()).endsWith(buildEndOfNotFoundErrorBody(widget.getId()));
        assertThat(response.getHeaders()).isEmpty();
    }

    @Test
    public void shouldReturnErrorIfIdNotProvided() {
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParamters(emptyMap())
                .withHeaders(emptyMap())
                .withRequestContext(new ProxyRequestContext());

        final APIGatewayProxyResponseEvent response = patchWidget.apply(request);

        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getBody()).startsWith("{\"errors\":[{\"id\":\"");
        assertThat(response.getBody()).endsWith("\",\"code\":\"WIDGET_ID_NOT_PROVIDED\",\"title\":\"widget id must be provided\",\"detail\":\"widget id must be provided\",\"meta\":{}}]}");
        assertThat(response.getHeaders()).isEmpty();
    }

    private static String buildEndOfNotFoundErrorBody(UUID id) {
        return String.format("\",\"code\":\"WIDGET_NOT_FOUND\",\"title\":\"no widgets found\",\"detail\":\"no widgets found with id [%s]\",\"meta\":{\"id\":\"%s\"}}]}", id, id);
    }

}
