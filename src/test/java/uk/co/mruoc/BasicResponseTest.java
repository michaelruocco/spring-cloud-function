package uk.co.mruoc;

import org.junit.Test;
import uk.co.mruoc.BasicResponse;
import uk.co.mruoc.Response;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.*;

public class BasicResponseTest {

    @Test
    public void shouldSetHeaders() {
        Map<String, String> headers = new HashMap<>();

        Response<Object> response = BasicResponse.builder()
                .headers(headers)
                .build();

        assertThat(response.getHeaders()).isEqualTo(headers);
    }

    @Test
    public void shouldSetStatusCode() {
        int statusCode = OK.value();

        Response<Object> response = BasicResponse.builder()
                .statusCode(statusCode)
                .build();

        assertThat(response.getStatusCode()).isEqualTo(statusCode);
    }

    @Test
    public void shouldSetBody() {
        Object body = new Object();

        Response<Object> response = BasicResponse.builder()
                .body(body)
                .build();

        assertThat(response.getBody()).isEqualTo(body);
    }

    @Test
    public void shouldPrintFieldsFromToString() {
        Response<Object> response = BasicResponse.builder().build();

        assertThat(response.toString()).isEqualTo("BasicResponse(body=null, statusCode=0, headers={})");
    }

}
