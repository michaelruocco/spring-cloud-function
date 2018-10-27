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
import uk.co.mruoc.model.Widget;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class PostWidgetTest {

    private final ObjectMapper mapper = JacksonConfiguration.getMapper();
    private final WidgetService service = mock(WidgetService.class);
    private final FakeWidget widget = new FakeWidget();

    private final PostWidget postWidget = new PostWidget();

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(postWidget, "mapper", mapper);
        ReflectionTestUtils.setField(postWidget, "service", service);
    }

    @Test
    public void shouldCreateWidgetAndReturn201Status() {
        given(service.createWidget(any(Widget.class))).willReturn(widget);
        final String body = widget.asJson();
        final ProxyRequestContext context = new ProxyRequestContext();
        context.setStage("dev");
        final Map<String, String> headers = new HashMap<>();
        headers.put("Host", "localhost");
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody(body)
                .withHeaders(headers)
                .withRequestContext(context)
                .withResource("/widgets");

        APIGatewayProxyResponseEvent response = postWidget.apply(request);

        assertThat(response.getStatusCode()).isEqualTo(201);
        assertThat(response.getBody()).isEqualTo(body);
        assertThat(response.getHeaders()).containsOnly(entry("Location", "https://localhost/dev/widgets/" + widget.getId()));
    }

    @Test
    public void shouldUpdateWidgetAndReturn200Status() {
        given(service.createWidget(any(Widget.class))).willReturn(widget);
        given(service.exists(widget.getId())).willReturn(true);
        final String body = widget.asJson();
        final ProxyRequestContext context = new ProxyRequestContext();
        context.setStage("dev");
        final Map<String, String> headers = new HashMap<>();
        headers.put("Host", "localhost");
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody(body)
                .withHeaders(headers)
                .withRequestContext(context)
                .withResource("/widgets");

        final APIGatewayProxyResponseEvent response = postWidget.apply(request);

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(body);
        assertThat(response.getHeaders()).containsOnly(entry("Location", "https://localhost/dev/widgets/" + widget.getId()));
    }

    @Test
    public void shouldReturnErrorIfRequestBodyIsInvalid() {
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody("invalid json");

        final APIGatewayProxyResponseEvent response = postWidget.apply(request);

        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.getBody()).isEqualTo("{\"title\":\"invalid json [invalid json]\",\"code\":\"INVALID_JSON\",\"meta\":{}}");
        assertThat(response.getHeaders()).isEmpty();
    }

}
