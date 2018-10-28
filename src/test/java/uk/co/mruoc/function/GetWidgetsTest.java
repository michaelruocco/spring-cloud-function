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

import java.util.Arrays;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class GetWidgetsTest {

    private final ObjectMapper mapper = JacksonConfiguration.getMapper();
    private final WidgetService service = mock(WidgetService.class);
    private final FakeWidget widget1 = new FakeWidget();
    private final FakeWidget widget2 = new FakeWidget();

    private final GetWidgets getWidgets = new GetWidgets();

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(getWidgets, "mapper", mapper);
        ReflectionTestUtils.setField(getWidgets, "service", service);
    }

    @Test
    public void shouldReturnWidgets() {
        given(service.getAllWidgets()).willReturn(Arrays.asList(widget1, widget2));
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParamters(emptyMap())
                .withHeaders(emptyMap())
                .withRequestContext(new ProxyRequestContext());
        final String expectedBody = String.format("{\"data\":[%s,%s]}", widget1.asJsonObject(), widget2.asJsonObject());

        final APIGatewayProxyResponseEvent response = getWidgets.apply(request);

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(expectedBody);
        assertThat(response.getHeaders()).isEmpty();
    }

    @Test
    public void shouldReturnEmptyArrayIfNoWidgetsFound() {
        given(service.getAllWidgets()).willReturn(emptyList());
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParamters(emptyMap())
                .withHeaders(emptyMap())
                .withRequestContext(new ProxyRequestContext());

        final APIGatewayProxyResponseEvent response = getWidgets.apply(request);

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("{\"data\":[]}");
        assertThat(response.getHeaders()).isEmpty();
    }

}
