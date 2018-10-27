package uk.co.mruoc.api;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class ErrorDocumentTest {

    @Test
    public void shouldReturnTitle() {
        String title = "my-title";

        ErrorDocument document = ErrorDocument.builder().title(title).build();

        assertThat(document.getTitle()).isEqualTo(title);
    }

    @Test
    public void shouldReturnCode() {
        String code = "my-code";

        ErrorDocument document = ErrorDocument.builder().code(code).build();

        assertThat(document.getCode()).isEqualTo(code);
    }

    @Test
    public void shouldSetMeta() {
        Map<String, Object> meta = new HashMap<>();

        ErrorDocument document = ErrorDocument.builder().meta(meta).build();

        assertThat(document.getMeta()).isEqualTo(meta);
    }

}
