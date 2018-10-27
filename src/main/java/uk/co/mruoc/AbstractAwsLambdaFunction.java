package uk.co.mruoc;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import uk.co.mruoc.api.ErrorDocument;
import uk.co.mruoc.function.InvalidJsonException;
import uk.co.mruoc.function.UnexpectedErrorException;

import java.io.IOException;
import java.util.function.Function;

@Slf4j
public abstract class AbstractAwsLambdaFunction<I, O> implements Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Autowired
    private ObjectMapper mapper;

    private final Class<I> inputType;

    public AbstractAwsLambdaFunction(Class<I> inputType) {
        this.inputType = inputType;
    }

    @Override
    public APIGatewayProxyResponseEvent apply(APIGatewayProxyRequestEvent requestEvent) {
        try {
            log.info("api gateway proxy request event received {}", requestEvent);
            Request<I> request = toRequest(requestEvent);
            log.info("processing request {}", request);
            Response<O> response = apply(request);
            log.info("response returned {}", response);
            APIGatewayProxyResponseEvent responseEvent = toEvent(response);
            log.info("api gateway proxy response event returned {}", responseEvent);
            return responseEvent;
        } catch (AbstractException e) {
            log.info("handling error", e);
            APIGatewayProxyResponseEvent responseEvent = toEvent(e);
            log.info("api gateway proxy response event returned {}", responseEvent);
            return responseEvent;
        }
    }

    public abstract Response<O> apply(Request<I> request);

    private Request<I> toRequest(APIGatewayProxyRequestEvent event) {
        String json = event.getBody();
        I body = toRequestBodyObject(json);
        return BasicRequest.<I>builder()
                .headers(event.getHeaders())
                .pathParameters(event.getPathParameters())
                .queryStringParameters(event.getQueryStringParameters())
                .body(body)
                .build();
    }

    private I toRequestBodyObject(String json) {
        try {
            if (StringUtils.isEmpty(json)) {
                return null;
            }
            return mapper.readValue(json, inputType);
        } catch (IOException e) {
            throw new InvalidJsonException(e, json);
        }
    }

    private APIGatewayProxyResponseEvent toEvent(Response<O> response) {
        String body = toJson(response.getBody());
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(response.getStatusCode())
                .withHeaders(response.getHeaders())
                .withBody(body);
    }

    private String toJson(O body) {
        try {
            if (body == null) {
                return "";
            }
            return mapper.writeValueAsString(body);
        } catch (IOException e) {
            throw new UnexpectedErrorException(e);
        }
    }

    private APIGatewayProxyResponseEvent toEvent(AbstractException e) {
        try {
            ErrorDocument error = toErrorDocument(e);
            String body = mapper.writeValueAsString(error);
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(e.getStatusCode())
                    .withHeaders(e.getHeaders())
                    .withBody(body);
        } catch (IOException ioe) {
            throw new UnexpectedErrorException(ioe);
        }
    }

    private ErrorDocument toErrorDocument(AbstractException e) {
        return ErrorDocument.builder()
                .title(e.getTitle())
                .code(e.getCode())
                .meta(e.getMeta())
                .build();
    }

}