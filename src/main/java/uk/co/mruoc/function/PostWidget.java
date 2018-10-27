package uk.co.mruoc.function;

import org.springframework.beans.factory.annotation.Autowired;
import uk.co.mruoc.AbstractAwsLambdaFunction;
import uk.co.mruoc.BasicResponse;
import uk.co.mruoc.Request;
import uk.co.mruoc.Response;
import uk.co.mruoc.api.WidgetDocument;
import uk.co.mruoc.model.Widget;

public class PostWidget extends AbstractAwsLambdaFunction<WidgetDocument, WidgetDocument> {

    private final WidgetConverter converter = new WidgetConverter();

    @Autowired
    private WidgetService service;

    public PostWidget() {
        super(WidgetDocument.class);
    }

    @Override
    public Response<WidgetDocument> apply(Request<WidgetDocument> request) {
        Widget widget = converter.toModel(request.getBody());
        Widget createdWidget = service.createWidget(widget);
        return toResponse(createdWidget);
    }

    private Response<WidgetDocument> toResponse(Widget body) {
        return BasicResponse.<WidgetDocument>builder()
                .statusCode(201)
                .body(converter.toDocument(body))
                .build();
    }

}
