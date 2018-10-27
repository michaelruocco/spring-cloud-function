package uk.co.mruoc.function;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import uk.co.mruoc.JacksonConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

public class PostWidgetTest {

    private static final long ID = 1;
    private static final long ALREADY_EXISTS_ID = 3;
    private static final String JSON_FORMAT = "{" +
            "\"id\":%s," +
            "\"description\":\"widget %s\"," +
            "\"cost\":{\"amount\":%s,\"currency\":\"GBP\"}," +
            "\"price\":{\"amount\":%s,\"currency\":\"GBP\"}" +
            "}";

    private final ObjectMapper mapper = JacksonConfiguration.getMapper();
    private final WidgetService service = new FakeWidgetService();

    private final PostWidget postWidget = new PostWidget();

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(postWidget, "mapper", mapper);
        ReflectionTestUtils.setField(postWidget, "service", service);
    }

    @Test
    public void shouldCreateWidget() {
        final String body = buildWidgetJson(ID);
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody(body);

        APIGatewayProxyResponseEvent response = postWidget.apply(request);

        assertThat(response.getStatusCode()).isEqualTo(201);
        assertThat(response.getBody()).isEqualTo(body);
        assertThat(response.getHeaders()).isEmpty();
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

    @Test
    public void shouldReturnErrorIfWidgetAlreadyExists() {
        final APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent()
                .withBody(buildWidgetJson(ALREADY_EXISTS_ID));

        final APIGatewayProxyResponseEvent response = postWidget.apply(request);

        assertThat(response.getStatusCode()).isEqualTo(422);
        assertThat(response.getBody()).isEqualTo("{\"title\":\"a widget already exists with id [3]\",\"code\":\"WIDGET_ALREADY_EXISTS\",\"meta\":{}}");
        assertThat(response.getHeaders()).isEmpty();
    }

    private static String buildWidgetJson(long id) {
        return String.format(JSON_FORMAT, id, id, (id + 10) + ".00", (id + 12) + ".00");
    }

}
