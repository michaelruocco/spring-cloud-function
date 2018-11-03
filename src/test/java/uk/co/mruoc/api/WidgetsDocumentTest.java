package uk.co.mruoc.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import uk.co.mruoc.JacksonConfiguration;
import uk.co.mruoc.file.ClasspathFileContentLoader;
import uk.co.mruoc.file.FileContentLoader;

import static org.assertj.core.api.Assertions.assertThat;

public class WidgetsDocumentTest {

    private final FileContentLoader contentLoader = new ClasspathFileContentLoader();
    private final ObjectMapper mapper = JacksonConfiguration.getMapper();

    @Test
    public void shouldSerializeToJson() throws JsonProcessingException {
        WidgetsDocument document = WidgetsDocumentFactory.build();
        final String expectedJson = contentLoader.loadContent("/multiple-widgets.json");

        String json = mapper.writeValueAsString(document);

        assertThat(json).isEqualToIgnoringWhitespace(expectedJson);
    }

}
