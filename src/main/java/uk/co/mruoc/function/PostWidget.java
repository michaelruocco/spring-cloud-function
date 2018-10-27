package uk.co.mruoc.function;

import org.springframework.beans.factory.annotation.Autowired;
import uk.co.mruoc.AbstractAwsLambdaFunction;
import uk.co.mruoc.BasicResponse;
import uk.co.mruoc.Request;
import uk.co.mruoc.Response;
import uk.co.mruoc.api.WidgetDocument;

public class PostWidget extends AbstractAwsLambdaFunction<WidgetDocument, WidgetDocument> {

    @Autowired
    private WidgetService service;

    public PostWidget() {
        super(WidgetDocument.class);
    }

    @Override
    public Response<WidgetDocument> apply(Request<WidgetDocument> request) {
        WidgetDocument widget = request.getBody();
        service.createWidget(widget);
        return toResponse(widget);
    }

    private Response<WidgetDocument> toResponse(WidgetDocument body) {
        return BasicResponse.<WidgetDocument>builder()
                .statusCode(201)
                .body(body)
                .build();
    }

}
