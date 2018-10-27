package uk.co.mruoc.function;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import uk.co.mruoc.JacksonConfiguration;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.*;
import static org.assertj.core.api.Assertions.assertThat;

public class GetWidgetTest {

    private static final long ID = 1;
    private static final long NOT_FOUND_ID = 3;
    private static final String JSON_FORMAT = "{" +
            "\"id\":%s," +
            "\"description\":\"widget %s\"," +
            "\"cost\":{\"amount\":%s,\"currency\":\"GBP\"}," +
            "\"price\":{\"amount\":%s,\"currency\":\"GBP\"}" +
            "}";

    private final ObjectMapper mapper = JacksonConfiguration.getMapper();
    private final WidgetService service = new FakeWidgetService();

    private final GetWidget getWidget = new GetWidget();

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(getWidget, "mapper", mapper);
        ReflectionTestUtils.setField(getWidget, "service", service);
    }

    @Test
    public void shouldReturnWidget() {
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParamters(buildPathParameters(ID));
        final String expectedBody = buildWidgetJson(ID);

        final APIGatewayProxyResponseEvent response = getWidget.apply(request);

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(expectedBody);
        assertThat(response.getHeaders()).isEmpty();
    }

    @Test
    public void shouldReturnErrorIfIdNotProvided() {
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParamters(emptyMap());

        final APIGatewayProxyResponseEvent response = getWidget.apply(request);

        assertThat(response.getStatusCode()).isEqualTo(400);
        assertThat(response.getBody()).isEqualTo("{\"title\":\"widget id must be provided\",\"code\":\"WIDGET_ID_NOT_PROVIDED\",\"meta\":{}}");
        assertThat(response.getHeaders()).isEmpty();
    }

    @Test
    public void shouldReturnErrorIfWidgetNotFound() {
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withPathParamters(buildPathParameters(NOT_FOUND_ID));

        final APIGatewayProxyResponseEvent response = getWidget.apply(request);

        assertThat(response.getStatusCode()).isEqualTo(404);
        assertThat(response.getBody()).isEqualTo("{\"title\":\"no widgets found with id [3]\",\"code\":\"WIDGET_NOT_FOUND\",\"meta\":{}}");
        assertThat(response.getHeaders()).isEmpty();
    }

    private static String buildWidgetJson(long id) {
        return String.format(JSON_FORMAT, id, id, (id + 10) + ".00", (id + 12) + ".00");
    }

    private static Map<String, String> buildPathParameters(long id) {
        Map<String, String> params = new HashMap<>();
        params.put("id", Long.toString(id));
        return unmodifiableMap(params);
    }

}
