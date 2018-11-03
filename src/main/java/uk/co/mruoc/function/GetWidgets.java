package uk.co.mruoc.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uk.co.mruoc.AbstractAwsLambdaFunction;
import uk.co.mruoc.BasicResponse;
import uk.co.mruoc.Request;
import uk.co.mruoc.Response;
import uk.co.mruoc.api.WidgetsDocument;
import uk.co.mruoc.model.Widget;

import static org.springframework.http.HttpStatus.OK;

@Slf4j
public class GetWidgets extends AbstractAwsLambdaFunction<Object, WidgetsDocument> {

    private final PageRequestFactory pageRequestFactory = new PageRequestFactory();
    private final WidgetConverter converter = new WidgetConverter();

    @Autowired
    private WidgetService service;

    public GetWidgets() {
        super(Object.class);
    }

    @Override
    public Response<WidgetsDocument> apply(Request<Object> request) {
        Pageable pageRequest = pageRequestFactory.build(request);
        log.info("getting page of widgets {}", pageRequest);
        Page<Widget> widgets = service.getWidgets(pageRequest);
        return toResponse(widgets);
    }

    private Response<WidgetsDocument> toResponse(Iterable<Widget> widgets) {
        return BasicResponse.<WidgetsDocument>builder()
                .statusCode(OK.value())
                .body(converter.toDocument(widgets))
                .build();
    }

}