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

import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NO_CONTENT;

public class DeleteWidgetTest {

    private final ObjectMapper mapper = JacksonConfiguration.getMapper();
    private final WidgetService service = mock(WidgetService.class);
    private final FakeWidget widget = new FakeWidget();
    private final PathParametersBuilder pathParametersBuilder = new PathParametersBuilder();

    private final DeleteWidget deleteWidget = new DeleteWidget();

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(deleteWidget, "mapper", mapper);
        ReflectionTestUtils.setField(deleteWidget, "service", service);
    }

    @Test
    public void shouldDeleteWidget() {
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParamters(pathParametersBuilder.withId(widget.getId()).build())
                .withHeaders(emptyMap())
                .withRequestContext(new ProxyRequestContext());

        final APIGatewayProxyResponseEvent response = deleteWidget.apply(request);

        assertThat(response.getStatusCode()).isEqualTo(NO_CONTENT.value());
        assertThat(response.getBody()).isEmpty();
        assertThat(response.getHeaders()).isEmpty();
    }

    @Test
    public void shouldReturnErrorIfIdNotProvided() {
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParamters(emptyMap())
                .withHeaders(emptyMap())
                .withRequestContext(new ProxyRequestContext());

        final APIGatewayProxyResponseEvent response = deleteWidget.apply(request);

        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST.value());
        assertThat(response.getBody()).startsWith("{\"errors\":[{\"id\":\"");
        assertThat(response.getBody()).endsWith("\",\"code\":\"WIDGET_ID_NOT_PROVIDED\",\"title\":\"widget id must be provided\",\"detail\":\"widget id must be provided\",\"meta\":{}}]}");
        assertThat(response.getHeaders()).isEmpty();
    }

}
