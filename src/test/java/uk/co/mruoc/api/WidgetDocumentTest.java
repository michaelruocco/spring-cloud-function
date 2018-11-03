package uk.co.mruoc.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import uk.co.mruoc.JacksonConfiguration;
import uk.co.mruoc.file.ClasspathFileContentLoader;
import uk.co.mruoc.file.FileContentLoader;

import static org.assertj.core.api.Assertions.assertThat;

public class WidgetDocumentTest {

    private final FileContentLoader contentLoader = new ClasspathFileContentLoader();
    private final ObjectMapper mapper = JacksonConfiguration.getMapper();

    @Test
    public void shouldSerializeToJson() throws JsonProcessingException {
        WidgetDocument document = WidgetDocumentFactory.build(1);
        final String expectedJson = contentLoader.loadContent("/single-widget.json");

        String json = mapper.writeValueAsString(document);

        assertThat(json).isEqualToIgnoringWhitespace(expectedJson);
    }

}
