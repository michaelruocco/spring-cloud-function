package uk.co.mruoc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.jackson.datatype.money.MoneyModule;

@Configuration
public class JacksonConfiguration {

    private static final ObjectMapper MAPPER = buildObjectMapper();

    @Bean
    public ObjectMapper objectMapper() {
        return getMapper();
    }

    public static ObjectMapper getMapper() {
        return MAPPER;
    }

    private static ObjectMapper buildObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new MoneyModule());
        return mapper;
    }

}