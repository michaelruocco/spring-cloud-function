package uk.co.mruoc.function;

import org.springframework.http.HttpStatus;
import uk.co.mruoc.AbstractException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WidgetNotFoundException extends AbstractException {

    private static final int STATUS = HttpStatus.NOT_FOUND.value();
    private static final String CODE = "WIDGET_NOT_FOUND";
    private static final String TITLE = "no widgets found";

    public WidgetNotFoundException(long id) {
        super(STATUS, CODE, TITLE, buildDetail(id), buildMeta(id));
    }

    private static String buildDetail(long id) {
        return String.format("no widgets found with id [%s]", id);
    }

    private static Map<String, Object> buildMeta(long id) {
        Map<String, Object> meta = new HashMap<>();
        meta.put("id", id);
        return Collections.unmodifiableMap(meta);
    }

}
