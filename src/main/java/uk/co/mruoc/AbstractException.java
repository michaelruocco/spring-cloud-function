package uk.co.mruoc;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractException extends RuntimeException {

    private final int statusCode;
    private final Map<String, String> headers;
    private final String title;
    private final String code;
    private final Map<String, Object> meta;

    public AbstractException(int statusCode, String title, String code) {
        this(statusCode, new HashMap<>(), title, code, new HashMap<>());
    }

    public AbstractException(Throwable cause, int statusCode, String title, String code) {
        this(cause, statusCode, new HashMap<>(), title, code, new HashMap<>());
    }

    public AbstractException(int statusCode, Map<String, String> headers, String title, String code, Map<String, Object> meta) {
        super(title);
        this.statusCode = statusCode;
        this.headers = headers;
        this.title = title;
        this.code = code;
        this.meta = meta;
    }

    public AbstractException(Throwable cause, int statusCode, Map<String, String> headers, String title, String code, Map<String, Object> meta) {
        super(title, cause);
        this.statusCode = statusCode;
        this.headers = headers;
        this.title = title;
        this.code = code;
        this.meta = meta;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }

    public Map<String, Object> getMeta() {
        return meta;
    }

}
