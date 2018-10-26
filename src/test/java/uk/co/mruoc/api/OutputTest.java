package uk.co.mruoc.api;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OutputTest {

    @Test
    public void shouldSetValueFromConstructor() {
        String value = "my-value";

        Output output = new Output(value);

        assertThat(output.getValue()).isEqualTo(value);
    }

    @Test
    public void shouldSetValue() {
        String value = "my-value";
        Output output = new Output();

        output.setValue(value);

        assertThat(output.getValue()).isEqualTo(value);
    }

}
