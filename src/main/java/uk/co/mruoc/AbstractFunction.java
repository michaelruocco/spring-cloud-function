package uk.co.mruoc;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Function;

@Slf4j
public abstract class AbstractFunction<I, O> implements Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Autowired
    private ObjectMapper mapper;

    private final Class<I> inputType;

    public AbstractFunction(Class<I> inputType) {
        this.inputType = inputType;
    }

    @Override
    public APIGatewayProxyResponseEvent apply(APIGatewayProxyRequestEvent requestEvent) {
        log.info("api gateway proxy request event received {}", requestEvent);
        Request<I> request = toRequest(requestEvent);
        log.info("processing request {}", request);
        Response<O> response = apply(request);
        log.info("response returned {}", response);
        APIGatewayProxyResponseEvent responseEvent = toEvent(response);
        log.info("api gateway proxy response event returned {}", responseEvent);
        return responseEvent;
    }

    public abstract Response<O> apply(Request<I> request);

    private Request<I> toRequest(APIGatewayProxyRequestEvent event) {
        try {
            I body = mapper.readValue(event.getBody(), inputType);
            return DefaultRequest.<I>builder()
                    .headers(event.getHeaders())
                    .pathParameters(event.getHeaders())
                    .queryStringParameters(event.getQueryStringParameters())
                    .body(body)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private APIGatewayProxyResponseEvent toEvent(Response<O> response) {
        try {
            String body = mapper.writeValueAsString(response.getBody());
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(response.getStatusCode())
                    .withBody(body)
                    .withHeaders(response.getHeaders());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}