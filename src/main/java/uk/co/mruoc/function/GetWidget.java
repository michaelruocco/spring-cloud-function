package uk.co.mruoc.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import uk.co.mruoc.AbstractAwsLambdaFunction;
import uk.co.mruoc.BasicResponse;
import uk.co.mruoc.Request;
import uk.co.mruoc.Response;
import uk.co.mruoc.api.WidgetDocument;

import java.util.Map;
import java.util.Optional;

@Slf4j
public class GetWidget extends AbstractAwsLambdaFunction<Object, WidgetDocument> {

    @Autowired
    private WidgetService service;

    public GetWidget() {
        super(Object.class);
    }

    @Override
    public Response<WidgetDocument> apply(Request<Object> request) {
        long id = extractId(request);
        log.info("extract id {} from request ", id);
        Optional<WidgetDocument> widget = service.getWidget(id);
        if (widget.isPresent()) {
            return toResponse(widget.get());
        }
        throw new WidgetNotFoundException(id);
    }

    private long extractId(Request<Object> request) {
        Map<String, String> pathParameters = request.getPathParameters();
        if (pathParameters.containsKey("id")) {
            return Long.parseLong(pathParameters.get("id"));
        }
        throw new IdNotProvidedException();
    }

    private Response<WidgetDocument> toResponse(WidgetDocument body) {
        return BasicResponse.<WidgetDocument>builder()
                .statusCode(200)
                .body(body)
                .build();
    }

}