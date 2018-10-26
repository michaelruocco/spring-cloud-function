package uk.co.mruoc.function;

import org.junit.Test;
import uk.co.mruoc.function.BasicRequest;
import uk.co.mruoc.function.Request;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class BasicRequestTest {

    @Test
    public void shouldSetHeaders() {
        Map<String, String> headers = new HashMap<>();

        Request<Object> request = BasicRequest.builder()
                .headers(headers)
                .build();

        assertThat(request.getHeaders()).isEqualTo(headers);
    }

    @Test
    public void shouldSetPathParameters() {
        Map<String, String> pathParameters = new HashMap<>();

        Request<Object> request = BasicRequest.builder()
                .pathParameters(pathParameters)
                .build();

        assertThat(request.getPathParameters()).isEqualTo(pathParameters);
    }

    @Test
    public void shouldSetQueryStringParameters() {
        Map<String, String> queryStringParameters = new HashMap<>();

        Request<Object> request = BasicRequest.builder()
                .queryStringParameters(queryStringParameters)
                .build();

        assertThat(request.getQueryStringParameters()).isEqualTo(queryStringParameters);
    }

    @Test
    public void shouldSetBody() {
        Object body = new Object();

        Request<Object> request = BasicRequest.builder()
                .body(body)
                .build();

        assertThat(request.getBody()).isEqualTo(body);
    }

    @Test
    public void shouldPrintFieldsFromToString() {
        Request<Object> request = BasicRequest.builder().build();

        assertThat(request.toString()).isEqualTo("BasicRequest(body=null, headers=null, pathParameters=null, queryStringParameters=null)");
    }

}
