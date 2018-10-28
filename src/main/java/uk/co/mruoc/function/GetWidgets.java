package uk.co.mruoc.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import uk.co.mruoc.AbstractAwsLambdaFunction;
import uk.co.mruoc.BasicResponse;
import uk.co.mruoc.Request;
import uk.co.mruoc.Response;
import uk.co.mruoc.api.WidgetsDocument;
import uk.co.mruoc.model.Widget;

import java.util.Map;
import java.util.UUID;

@Slf4j
public class GetWidgets extends AbstractAwsLambdaFunction<Object, WidgetsDocument> {

    private final WidgetConverter converter = new WidgetConverter();

    @Autowired
    private WidgetService service;

    public GetWidgets() {
        super(Object.class);
    }

    @Override
    public Response<WidgetsDocument> apply(Request<Object> request) {
        log.info("getting all widgets");
        Iterable<Widget> widgets = service.getAllWidgets();
        return toResponse(widgets);
    }

    private Response<WidgetsDocument> toResponse(Iterable<Widget> widgets) {
        return BasicResponse.<WidgetsDocument>builder()
                .statusCode(200)
                .body(converter.toDocument(widgets))
                .build();
    }

}