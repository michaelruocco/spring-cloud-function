package uk.co.mruoc.function;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import uk.co.mruoc.HeadersBuilder;
import uk.co.mruoc.JacksonConfiguration;
import uk.co.mruoc.model.FakeWidget;
import uk.co.mruoc.model.Widget;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

public class PostWidgetTest {

    private final ObjectMapper mapper = JacksonConfiguration.getMapper();
    private final WidgetService service = mock(WidgetService.class);
    private final FakeWidget widget = new FakeWidget();
    private final HeadersBuilder headersBuilder = new HeadersBuilder();
    private final ProxyRequestContextBuilder proxyRequestContextBuilder = new ProxyRequestContextBuilder();

    private final PostWidget postWidget = new PostWidget();

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(postWidget, "mapper", mapper);
        ReflectionTestUtils.setField(postWidget, "service", service);
    }

    @Test
    public void shouldCreateWidgetAndReturnCreatedStatus() {
        given(service.createWidget(any(Widget.class))).willReturn(widget);
        final String body = widget.asJsonDocument();
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody(body)
                .withHeaders(headersBuilder.withHost("localhost").build())
                .withRequestContext(proxyRequestContextBuilder.withStage("test").build())
                .withResource("/widgets");

        APIGatewayProxyResponseEvent response = postWidget.apply(request);

        assertThat(response.getStatusCode()).isEqualTo(CREATED.value());
        assertThat(response.getBody()).isEqualTo(body);
        assertThat(response.getHeaders()).containsOnly(entry("Location", "https://localhost/test/widgets/" + widget.getId()));
    }

    @Test
    public void shouldUpdateWidgetAndReturnOkStatus() {
        given(service.createWidget(any(Widget.class))).willReturn(widget);
        given(service.exists(widget.getId())).willReturn(true);
        final String body = widget.asJsonDocument();
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody(body)
                .withHeaders(headersBuilder.withHost("localhost").build())
                .withRequestContext(proxyRequestContextBuilder.withStage("test").build())
                .withResource("/widgets");

        final APIGatewayProxyResponseEvent response = postWidget.apply(request);

        assertThat(response.getStatusCode()).isEqualTo(OK.value());
        assertThat(response.getBody()).isEqualTo(body);
        assertThat(response.getHeaders()).containsOnly(entry("Location", "https://localhost/test/widgets/" + widget.getId()));
    }

    @Test
    public void shouldReturnErrorIfRequestBodyIsInvalid() {
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody("invalid json");

        final APIGatewayProxyResponseEvent response = postWidget.apply(request);

        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getBody()).startsWith("{\"errors\":[{\"id\":\"");
        assertThat(response.getBody()).endsWith("\",\"code\":\"INVALID_JSON\",\"title\":\"json is invalid\",\"detail\":\"json [invalid json] is invalid: Unrecognized token 'invalid': was expecting ('true', 'false' or 'null')\\n at [Source: (String)\\\"invalid json\\\"; line: 1, column: 8]\",\"meta\":{}}]}");
        assertThat(response.getHeaders()).isEmpty();
    }

}
