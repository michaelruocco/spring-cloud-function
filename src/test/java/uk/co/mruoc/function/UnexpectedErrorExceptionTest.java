package uk.co.mruoc.function;

import org.junit.Test;
import uk.co.mruoc.AbstractException;

import static org.assertj.core.api.Assertions.assertThat;

public class UnexpectedErrorExceptionTest {

    private final Throwable cause = new Exception("test cause");

    private final AbstractException exception = new UnexpectedErrorException(cause);

    @Test
    public void shouldReturnCorrectValues() {
        assertThat(exception.getStatusCode()).isEqualTo(500);
        assertThat(exception.getHeaders()).isEmpty();
        assertThat(exception.getTitle()).isEqualTo("an unexpected error occurred");
        assertThat(exception.getCode()).isEqualTo("UNEXPECTED_ERROR");
        assertThat(exception.getMeta()).isEmpty();
    }

}
