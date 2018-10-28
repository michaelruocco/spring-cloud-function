package uk.co.mruoc.api;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


public class ErrorDataTest {

    @Test
    public void shouldReturnId() {
        UUID id = UUID.randomUUID();

        ErrorData data = ErrorData.builder().id(id).build();

        assertThat(data.getId()).isEqualTo(id);
    }

    @Test
    public void shouldReturnCode() {
        String code = "my-code";

        ErrorData data = ErrorData.builder().code(code).build();

        assertThat(data.getCode()).isEqualTo(code);
    }

    @Test
    public void shouldReturnTitle() {
        String title = "my-title";

        ErrorData data = ErrorData.builder().title(title).build();

        assertThat(data.getTitle()).isEqualTo(title);
    }

    @Test
    public void shouldReturnDetail() {
        String detail = "my-detail";

        ErrorData data = ErrorData.builder().detail(detail).build();

        assertThat(data.getDetail()).isEqualTo(detail);
    }

    @Test
    public void shouldSetMeta() {
        Map<String, Object> meta = new HashMap<>();

        ErrorData data = ErrorData.builder().meta(meta).build();

        assertThat(data.getMeta()).isEqualTo(meta);
    }

}
