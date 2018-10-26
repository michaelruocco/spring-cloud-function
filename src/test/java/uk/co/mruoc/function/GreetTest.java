package uk.co.mruoc.function;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.UncheckedIOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

public class GreetTest {

    private static final String REQUEST_BODY = "{\"value\":\"Ruocco\"}";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final Greet greet = new Greet();

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(greet, "mapper", MAPPER);
    }

    @Test
    public void shouldReturnGreetingForRequestInputValue() {
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent().withBody(REQUEST_BODY);

        APIGatewayProxyResponseEvent response = greet.apply(request);

        assertThat(response.getBody()).isEqualTo("{\"value\":\"Hello Ruocco, and welcome to Spring Cloud Function!!!\"}");
    }

    @Test
    public void shouldThrowExceptionIfInputIsInvalid() {
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent().withBody("blah");

        Throwable thrown = catchThrowable(() -> greet.apply(request));

        assertThat(thrown)
                .isInstanceOf(UncheckedIOException.class)
                .hasCauseInstanceOf(JsonParseException.class);
    }

    @Test
    public void shouldReturnOkStatusCode() {
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent().withBody(REQUEST_BODY);

        APIGatewayProxyResponseEvent response = greet.apply(request);

        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void shouldReturnNullHeaders() {
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent().withBody(REQUEST_BODY);

        APIGatewayProxyResponseEvent response = greet.apply(request);

        assertThat(response.getHeaders()).isNull();
    }

}
