package uk.co.mruoc;

import org.springframework.http.HttpStatus;

public class UnexpectedErrorException extends AbstractException {

    private static final int STATUS = HttpStatus.INTERNAL_SERVER_ERROR.value();
    private static final String CODE = "UNEXPECTED_ERROR";
    private static final String TITLE = "an unexpected error occurred";

    public UnexpectedErrorException(Throwable cause) {
        this(cause.getMessage());
    }

    public UnexpectedErrorException(String detail) {
        super(STATUS, CODE, TITLE, detail);
    }

}
