package uk.co.mruoc.function;

import org.springframework.beans.factory.annotation.Autowired;
import uk.co.mruoc.AbstractAwsLambdaFunction;
import uk.co.mruoc.BasicResponse;
import uk.co.mruoc.Request;
import uk.co.mruoc.Response;
import uk.co.mruoc.api.WidgetDocument;
import uk.co.mruoc.model.Widget;

import java.util.HashMap;
import java.util.Map;

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
        int statusCode = getStatusCode(widget.getId());
        Widget createdWidget = service.createWidget(widget);
        String uri = buildResourceUri(request.getUri(), createdWidget.getId());
        return toResponse(statusCode, createdWidget, uri);
    }

    private Response<WidgetDocument> toResponse(int statusCode, Widget body, String uri) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Location", uri);
        return BasicResponse.<WidgetDocument>builder()
                .statusCode(statusCode)
                .body(converter.toDocument(body))
                .headers(headers)
                .build();
    }

    private String buildResourceUri(String baseUri, long id) {
        return String.format("%s/%s", baseUri, id);
    }

    private int getStatusCode(long id) {
        if (service.exists(id)) {
            return 200;
        }
        return 201;
    }

}
