package uk.co.mruoc.function;

import uk.co.mruoc.AbstractException;

public class UnexpectedErrorException extends AbstractException {

    public UnexpectedErrorException(Throwable cause) {
        super(cause, 500, "an unexpected error occurred", "UNEXPECTED_ERROR");
    }

    public UnexpectedErrorException(String title) {
        super(500, title, "UNEXPECTED_ERROR");
    }

}
