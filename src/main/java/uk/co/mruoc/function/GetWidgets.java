package uk.co.mruoc.function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uk.co.mruoc.AbstractAwsApiGatewayLambdaFunction;
import uk.co.mruoc.BasicResponse;
import uk.co.mruoc.PageRequestFactory;
import uk.co.mruoc.Request;
import uk.co.mruoc.Response;
import uk.co.mruoc.api.WidgetsDocument;
import uk.co.mruoc.model.Widget;

import static org.springframework.http.HttpStatus.OK;

public class GetWidgets extends AbstractAwsApiGatewayLambdaFunction<Object, WidgetsDocument> {

    private final PageRequestFactory pageRequestFactory = new PageRequestFactory();
    private final WidgetConverter converter = new WidgetConverter();

    @Autowired
    private WidgetService service;

    public GetWidgets() {
        super(Object.class);
    }

    @Override
    public Response<WidgetsDocument> apply(Request<Object> request) {
        final Pageable pageRequest = pageRequestFactory.build(request);
        final Page<Widget> widgets = service.getWidgets(pageRequest);
        return BasicResponse.<WidgetsDocument>builder()
                .statusCode(OK.value())
                .body(converter.toDocument(widgets))
                .build();
    }

}