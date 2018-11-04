package uk.co.mruoc;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.*;

public class BasicResponseTest {

    @Test
    public void shouldSetHeaders() {
        final Map<String, String> headers = new HashMap<>();

        final Response<Object> response = BasicResponse.builder()
                .headers(headers)
                .build();

        assertThat(response.getHeaders()).isEqualTo(headers);
    }

    @Test
    public void shouldSetStatusCode() {
        final int statusCode = OK.value();

        final Response<Object> response = BasicResponse.builder()
                .statusCode(statusCode)
                .build();

        assertThat(response.getStatusCode()).isEqualTo(statusCode);
    }

    @Test
    public void shouldSetBody() {
        final Object body = new Object();

        final Response<Object> response = BasicResponse.builder()
                .body(body)
                .build();

        assertThat(response.getBody()).isEqualTo(body);
    }

    @Test
    public void shouldPrintFieldsFromToString() {
        final Response<Object> response = BasicResponse.builder().build();

        assertThat(response.toString()).isEqualTo("BasicResponse(body=null, statusCode=0, headers={})");
    }

    @Test
    public void shouldReturnHasHeader() {
        final Map<String, String> headers = new HeadersBuilder()
                .withHost("localhost")
                .build();

        final Response<Object> request = BasicResponse.builder()
                .headers(headers)
                .build();

        assertThat(request.hasHeader("Host")).isTrue();
        assertThat(request.hasHeader("Location")).isFalse();
    }

    @Test
    public void shouldReturnHeader() {
        final String host = "localhost";
        final Map<String, String> headers = new HeadersBuilder()
                .withHost(host)
                .build();

        final Response<Object> request = BasicResponse.builder()
                .headers(headers)
                .build();

        assertThat(request.getHeader("Host")).isEqualTo(host);
    }


}
