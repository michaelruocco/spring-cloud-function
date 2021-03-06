package uk.co.mruoc.function;

import org.springframework.beans.factory.annotation.Autowired;
import uk.co.mruoc.AbstractAwsApiGatewayLambdaFunction;
import uk.co.mruoc.BasicResponse;
import uk.co.mruoc.HeadersBuilder;
import uk.co.mruoc.Request;
import uk.co.mruoc.ResourceUriBuilder;
import uk.co.mruoc.Response;
import uk.co.mruoc.api.WidgetDocument;
import uk.co.mruoc.model.Widget;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

public class PostWidget extends AbstractAwsApiGatewayLambdaFunction<WidgetDocument, WidgetDocument> {

    private final HeadersBuilder headersBuilder = new HeadersBuilder();
    private final WidgetConverter converter = new WidgetConverter();

    @Autowired
    private WidgetService service;

    public PostWidget() {
        super(WidgetDocument.class);
    }

    @Override
    public Response<WidgetDocument> apply(Request<WidgetDocument> request) {
        final Widget widget = converter.toModel(request.getBody());
        final int statusCode = getStatusCode(widget.getId());
        final Widget createdWidget = service.createWidget(widget);
        final String uri = ResourceUriBuilder.build(request.getUri(), createdWidget.getId());
        return BasicResponse.<WidgetDocument>builder()
                .statusCode(statusCode)
                .body(converter.toDocument(createdWidget))
                .headers(headersBuilder.withLocation(uri).build())
                .build();
    }

    private int getStatusCode(UUID id) {
        if (service.exists(id)) {
            return OK.value();
        }
        return CREATED.value();
    }

}
