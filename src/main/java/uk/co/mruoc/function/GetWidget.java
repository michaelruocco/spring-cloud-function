package uk.co.mruoc.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import uk.co.mruoc.AbstractAwsApiGatewayLambdaFunction;
import uk.co.mruoc.BasicResponse;
import uk.co.mruoc.IdExtractor;
import uk.co.mruoc.Request;
import uk.co.mruoc.Response;
import uk.co.mruoc.api.WidgetDocument;
import uk.co.mruoc.model.Widget;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
public class GetWidget extends AbstractAwsApiGatewayLambdaFunction<Object, WidgetDocument> {

    private final WidgetConverter converter = new WidgetConverter();
    private final IdExtractor idExtractor = new IdExtractor();

    @Autowired
    private WidgetService service;

    public GetWidget() {
        super(Object.class);
    }

    @Override
    public Response<WidgetDocument> apply(Request<Object> request) {
        final UUID id = idExtractor.extract(request);
        log.info("extract id {} from request", id);
        final Optional<Widget> widget = service.getWidget(id);
        if (widget.isPresent()) {
            return toResponse(widget.get());
        }
        throw new WidgetNotFoundException(id);
    }

    private Response<WidgetDocument> toResponse(Widget widget) {
        return BasicResponse.<WidgetDocument>builder()
                .statusCode(OK.value())
                .body(converter.toDocument(widget))
                .build();
    }

}