package uk.co.mruoc.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import uk.co.mruoc.AbstractAwsApiGatewayLambdaFunction;
import uk.co.mruoc.BasicResponse;
import uk.co.mruoc.IdExtractor;
import uk.co.mruoc.Request;
import uk.co.mruoc.Response;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@Slf4j
public class DeleteWidget extends AbstractAwsApiGatewayLambdaFunction<Object, Object> {

    private final IdExtractor idExtractor = new IdExtractor();

    @Autowired
    private WidgetService service;

    public DeleteWidget() {
        super(Object.class);
    }

    @Override
    public Response<Object> apply(Request<Object> request) {
        final UUID id = idExtractor.extract(request);
        log.info("extract id {} from request", id);
        service.deleteWidget(id);
        return BasicResponse.builder()
                .statusCode(NO_CONTENT.value())
                .build();
    }

}