package uk.co.mruoc;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class UnexpectedErrorException extends AbstractException {

    private static final int STATUS = INTERNAL_SERVER_ERROR.value();
    private static final String CODE = "UNEXPECTED_ERROR";
    private static final String TITLE = "an unexpected error occurred";

    public UnexpectedErrorException(Throwable cause) {
        this(cause, cause.getMessage());
    }

    public UnexpectedErrorException(Throwable cause, String detail) {
        super(cause, STATUS, CODE, TITLE, detail);
    }

}
