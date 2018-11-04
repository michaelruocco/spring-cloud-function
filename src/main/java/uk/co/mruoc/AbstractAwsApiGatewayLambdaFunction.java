package uk.co.mruoc;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import uk.co.mruoc.api.ErrorData;
import uk.co.mruoc.api.ErrorDocument;

import java.io.IOException;
import java.util.function.Function;

@Slf4j
public abstract class AbstractAwsApiGatewayLambdaFunction<I, O> implements Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Autowired
    private ObjectMapper mapper;

    private final Class<I> inputType;

    public AbstractAwsApiGatewayLambdaFunction(Class<I> inputType) {
        this.inputType = inputType;
    }

    @Override
    public APIGatewayProxyResponseEvent apply(APIGatewayProxyRequestEvent requestEvent) {
        try {
            return handle(requestEvent);
        } catch (AbstractException e) {
            return handle(e);
        }
    }

    private APIGatewayProxyResponseEvent handle(APIGatewayProxyRequestEvent event) {
        log.info("api gateway proxy request event received {}", event);
        Request<I> request = toRequest(event);
        log.info("processing request {}", request);
        Response<O> response = apply(request);
        log.info("response returned {}", response);
        APIGatewayProxyResponseEvent responseEvent = toEvent(response);
        log.info("api gateway proxy response event returned {}", responseEvent);
        return responseEvent;
    }

    private APIGatewayProxyResponseEvent handle(AbstractException e) {
        log.info("handling error {}", e.getMessage(), e);
        APIGatewayProxyResponseEvent responseEvent = toEvent(e);
        log.info("api gateway proxy response event returned {}", responseEvent);
        return responseEvent;
    }

    public abstract Response<O> apply(Request<I> request);

    private Request<I> toRequest(APIGatewayProxyRequestEvent event) {
        String json = event.getBody();
        I body = toRequestBodyObject(json);
        String uri = extractUri(event);
        return BasicRequest.<I>builder()
                .headers(event.getHeaders())
                .pathParameters(event.getPathParameters())
                .queryStringParameters(event.getQueryStringParameters())
                .uri(uri)
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

    private String extractUri(APIGatewayProxyRequestEvent event) {
        String hostname = event.getHeaders().get("Host");
        String stage = event.getRequestContext().getStage();
        String resource = event.getResource();
        return String.format("https://%s/%s%s", hostname, stage, resource);
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
                    .withStatusCode(e.getStatus())
                    .withHeaders(e.getHeaders())
                    .withBody(body);
        } catch (IOException ioe) {
            throw new UnexpectedErrorException(ioe);
        }
    }

    private ErrorDocument toErrorDocument(AbstractException e) {
        return new ErrorDocument(ErrorData.builder()
                .id(e.getId())
                .code(e.getCode())
                .title(e.getTitle())
                .detail(e.getDetail())
                .meta(e.getMeta())
                .build());
    }

}