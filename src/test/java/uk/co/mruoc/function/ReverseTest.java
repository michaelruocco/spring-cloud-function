package uk.co.mruoc.function;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class ReverseTest {

    private static final String REQUEST_BODY = "{\"value\":\"Ruocco\"}";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final Reverse reverse = new Reverse();

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(reverse, "mapper", MAPPER);
    }

    @Test
    public void shouldReturnGreetingForRequestBodyInputValue() {
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent().withBody(REQUEST_BODY);

        APIGatewayProxyResponseEvent response = reverse.apply(request);

        assertThat(response.getBody()).isEqualTo("{\"value\":\"occouR\"}");
    }

    @Test
    public void shouldReturnOkStatusCode() {
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent().withBody(REQUEST_BODY);

        APIGatewayProxyResponseEvent response = reverse.apply(request);

        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void shouldReturnNullHeaders() {
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent().withBody(REQUEST_BODY);

        APIGatewayProxyResponseEvent response = reverse.apply(request);

        assertThat(response.getHeaders()).isNull();
    }

}
