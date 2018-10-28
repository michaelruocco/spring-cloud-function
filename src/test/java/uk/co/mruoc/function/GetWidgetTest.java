package uk.co.mruoc.function;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent.ProxyRequestContext;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import uk.co.mruoc.JacksonConfiguration;
import uk.co.mruoc.model.FakeWidget;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static java.util.Collections.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class GetWidgetTest {

    private final ObjectMapper mapper = JacksonConfiguration.getMapper();
    private final WidgetService service = mock(WidgetService.class);
    private final FakeWidget widget = new FakeWidget();

    private final GetWidget getWidget = new GetWidget();

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(getWidget, "mapper", mapper);
        ReflectionTestUtils.setField(getWidget, "service", service);
    }

    @Test
    public void shouldReturnWidget() {
        given(service.getWidget(widget.getId())).willReturn(Optional.of(widget));
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParamters(buildPathParameters(widget.getId()))
                .withHeaders(emptyMap())
                .withRequestContext(new ProxyRequestContext());
        final String expectedBody = widget.asJsonDocument();

        final APIGatewayProxyResponseEvent response = getWidget.apply(request);

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(expectedBody);
        assertThat(response.getHeaders()).isEmpty();
    }

    @Test
    public void shouldReturnErrorIfWidgetNotFound() {
        given(service.getWidget(widget.getId())).willReturn(Optional.empty());
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParamters(buildPathParameters(widget.getId()))
                .withHeaders(emptyMap())
                .withRequestContext(new ProxyRequestContext());

        final APIGatewayProxyResponseEvent response = getWidget.apply(request);

        assertThat(response.getStatusCode()).isEqualTo(404);
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

        final APIGatewayProxyResponseEvent response = getWidget.apply(request);

        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.getBody()).startsWith("{\"errors\":[{\"id\":\"");
        assertThat(response.getBody()).endsWith("\",\"code\":\"WIDGET_ID_NOT_PROVIDED\",\"title\":\"widget id must be provided\",\"detail\":\"widget id must be provided\",\"meta\":{}}]}");
        assertThat(response.getHeaders()).isEmpty();
    }

    private static Map<String, String> buildPathParameters(UUID id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id.toString());
        return unmodifiableMap(params);
    }

    private static String buildEndOfNotFoundErrorBody(UUID id) {
        return String.format("\",\"code\":\"WIDGET_NOT_FOUND\",\"title\":\"no widgets found\",\"detail\":\"no widgets found with id [%s]\",\"meta\":{\"id\":\"%s\"}}]}", id, id);
    }

}
