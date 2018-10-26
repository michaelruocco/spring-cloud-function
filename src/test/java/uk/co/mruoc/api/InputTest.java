package uk.co.mruoc.api;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InputTest {

    @Test
    public void shouldSetValueFromConstructor() {
        String value = "my-value";

        Input input = new Input(value);

        assertThat(input.getValue()).isEqualTo(value);
    }

    public void shouldSetValue() {
        String value = "my-value";
        Input input = new Input();

        input.setValue(value);

        assertThat(input.getValue()).isEqualTo(value);
    }

}
