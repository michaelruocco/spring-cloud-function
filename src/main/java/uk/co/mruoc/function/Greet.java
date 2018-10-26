package uk.co.mruoc.function;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import uk.co.mruoc.Request;
import uk.co.mruoc.Response;

import java.util.function.Function;

@Slf4j
public class Greet implements Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public APIGatewayProxyResponseEvent apply(APIGatewayProxyRequestEvent message) {
        try {
            log.info("got message {}", message);
            Request request = mapper.readValue(message.getBody(), Request.class);
            log.info("got request {}", request);
            APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
            Response response = new Response();
            response.setValue("Hello " + request.getValue() + ", and welcome to Spring Cloud Function!!!");
            responseEvent.withBody(mapper.writeValueAsString(response));
            responseEvent.setStatusCode(200);
            return responseEvent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}