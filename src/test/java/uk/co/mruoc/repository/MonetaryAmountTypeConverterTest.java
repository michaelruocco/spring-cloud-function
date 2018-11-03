package uk.co.mruoc.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.javamoney.moneta.Money;
import org.junit.Test;
import uk.co.mruoc.UnexpectedErrorException;

import javax.money.MonetaryAmount;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class MonetaryAmountTypeConverterTest {

    private final ObjectMapper mapper = mock(ObjectMapper.class);

    private final MonetaryAmountTypeConverter converter = new MonetaryAmountTypeConverter(mapper);

    @Test
    public void shouldThrowExceptionIfObjectCannotBeSerialized() throws JsonProcessingException {
        Throwable cause = mock(JsonProcessingException.class);
        MonetaryAmount amount = Money.of(1, "GBP");
        given(mapper.writeValueAsString(amount)).willThrow(cause);

        Throwable thrown = catchThrowable(() -> converter.convert(amount));

        assertThat(thrown)
                .isInstanceOf(UnexpectedErrorException.class)
                .hasCause(cause);
    }

    @Test
    public void shouldThrowExceptionIfObjectCannotBeDeserialized() throws IOException {
        Throwable cause = new IOException("test exception");
        String json = "{}";
        given(mapper.readValue(json, Money.class)).willThrow(cause);

        Throwable thrown = catchThrowable(() -> converter.unconvert(json));

        assertThat(thrown)
                .isInstanceOf(UnexpectedErrorException.class)
                .hasCause(cause);
    }

}
