package uk.co.mruoc.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import uk.co.mruoc.JacksonConfiguration;
import uk.co.mruoc.file.ClasspathFileContentLoader;
import uk.co.mruoc.file.FileContentLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.util.Collections.emptyMap;
import static java.util.Collections.unmodifiableMap;
import static org.assertj.core.api.Assertions.assertThat;

public class ErrorDocumentTest {

    private final FileContentLoader contentLoader = new ClasspathFileContentLoader();
    private final ObjectMapper mapper = JacksonConfiguration.getMapper();

    @Test
    public void shouldSerializeToJsonWithSingleError() throws JsonProcessingException {
        ErrorDocument document = new ErrorDocument(buildErrorData(1));
        final String expectedJson = contentLoader.loadContent("/single-error.json");

        String json = mapper.writeValueAsString(document);

        assertThat(json).isEqualToIgnoringWhitespace(expectedJson);
    }

    @Test
    public void shouldSerializeToJsonWithMultipleErrors() throws JsonProcessingException {
        ErrorDocument document = new ErrorDocument(buildErrorData(1), buildErrorData(2));
        final String expectedJson = contentLoader.loadContent("/multiple-errors.json");

        String json = mapper.writeValueAsString(document);

        assertThat(json).isEqualToIgnoringWhitespace(expectedJson);
    }

    private static ErrorData buildErrorData(int number) {
        return ErrorData.builder()
                .id(getId(number))
                .code("TEST_CODE_" + number)
                .title("test title " + number)
                .detail("test detail " + number)
                .meta(buildMeta(number))
                .build();
    }

    private static Map<String, Object> buildMeta(int number) {
        if (number == 1) {
            Map<String, Object> meta = new HashMap<>();
            meta.put("key1", "value1");
            meta.put("key2", "value2");
            return unmodifiableMap(meta);
        }
        return emptyMap();
    }

    private static UUID getId(int number) {
        if (number == 1) {
            return UUID.fromString("1fb5b8c6-e4f3-4297-a3aa-156d94a3fc5a");
        }
        return UUID.fromString("18a4786f-8673-4a94-ba11-2199cf049ab1");
    }

}
