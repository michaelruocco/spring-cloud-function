package uk.co.mruoc.function;

import org.springframework.http.HttpStatus;
import uk.co.mruoc.AbstractException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WidgetNotFoundException extends AbstractException {

    private static final int STATUS = HttpStatus.NOT_FOUND.value();
    private static final String CODE = "WIDGET_NOT_FOUND";
    private static final String TITLE = "no widgets found";

    public WidgetNotFoundException(UUID id) {
        super(STATUS, CODE, TITLE, buildDetail(id), buildMeta(id));
    }

    private static String buildDetail(UUID id) {
        return String.format("no widgets found with id [%s]", id);
    }

    private static Map<String, Object> buildMeta(UUID id) {
        Map<String, Object> meta = new HashMap<>();
        meta.put("id", id);
        return Collections.unmodifiableMap(meta);
    }

}
