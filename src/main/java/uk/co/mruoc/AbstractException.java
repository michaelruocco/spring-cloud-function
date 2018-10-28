package uk.co.mruoc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractException extends RuntimeException {

    private final int status;
    private final Map<String, String> headers;
    private final UUID id;
    private final String code;
    private final String title;
    private final String detail;
    private final Map<String, Object> meta;

    public AbstractException(int status, String code, String title, String detail) {
        this(status, new HashMap<>(), code, title, detail, new HashMap<>());
    }

    public AbstractException(Throwable cause, int status, String code, String title, String detail) {
        this(cause, status, new HashMap<>(), code, title, detail, new HashMap<>());
    }

    public AbstractException(int status, String code, String title, String detail, Map<String, Object> meta) {
        this(status, new HashMap<>(), code, title, detail, meta);
    }

    public AbstractException(int status, Map<String, String> headers, String code, String title, String detail, Map<String, Object> meta) {
        super(detail);
        this.status = status;
        this.headers = headers;
        this.id = UUID.randomUUID();
        this.code = code;
        this.title = title;
        this.detail = detail;
        this.meta = meta;
    }

    public AbstractException(Throwable cause, int status, Map<String, String> headers, String code, String title, String detail, Map<String, Object> meta) {
        super(title, cause);
        this.id = UUID.randomUUID();
        this.status = status;
        this.headers = headers;
        this.code = code;
        this.title = title;
        this.detail = detail;
        this.meta = meta;
    }

    public int getStatus() {
        return status;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public UUID getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    public Map<String, Object> getMeta() {
        return meta;
    }

}
