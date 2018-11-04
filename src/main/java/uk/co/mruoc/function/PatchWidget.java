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

import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
public class PatchWidget extends AbstractAwsApiGatewayLambdaFunction<WidgetDocument, WidgetDocument> {

    private final WidgetConverter converter = new WidgetConverter();
    private final IdExtractor idExtractor = new IdExtractor();

    @Autowired
    private WidgetService service;

    public PatchWidget() {
        super(WidgetDocument.class);
    }

    @Override
    public Response<WidgetDocument> apply(Request<WidgetDocument> request) {
        final UUID id = idExtractor.extract(request);
        log.info("extract id {} from request", id);
        final Widget widget = converter.toModel(request.getBody());
        final Widget updatedWidget = service.updateWidget(id, widget);
        return toResponse(updatedWidget);
    }

    private Response<WidgetDocument> toResponse(Widget body) {
        return BasicResponse.<WidgetDocument>builder()
                .statusCode(OK.value())
                .body(converter.toDocument(body))
                .build();
    }

}
