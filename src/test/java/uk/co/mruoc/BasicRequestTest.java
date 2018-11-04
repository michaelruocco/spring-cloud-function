package uk.co.mruoc;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class BasicRequestTest {

    @Test
    public void shouldSetHeaders() {
        final Map<String, String> headers = new HashMap<>();

        final Request<Object> request = BasicRequest.builder()
                .headers(headers)
                .build();

        assertThat(request.getHeaders()).isEqualTo(headers);
    }

    @Test
    public void shouldSetPathParameters() {
        final Map<String, String> pathParameters = new HashMap<>();

        final Request<Object> request = BasicRequest.builder()
                .pathParameters(pathParameters)
                .build();

        assertThat(request.getPathParameters()).isEqualTo(pathParameters);
    }

    @Test
    public void shouldSetQueryStringParameters() {
        final Map<String, String> queryStringParameters = new HashMap<>();

        final Request<Object> request = BasicRequest.builder()
                .queryStringParameters(queryStringParameters)
                .build();

        assertThat(request.getQueryStringParameters()).isEqualTo(queryStringParameters);
    }

    @Test
    public void shouldSetBody() {
        final Object body = new Object();

        final Request<Object> request = BasicRequest.builder()
                .body(body)
                .build();

        assertThat(request.getBody()).isEqualTo(body);
    }

    @Test
    public void shouldPrintFieldsFromToString() {
        final Request<Object> request = BasicRequest.builder().build();

        assertThat(request.toString()).isEqualTo("BasicRequest(body=null, uri=null, headers=null, pathParameters=null, queryStringParameters=null)");
    }

    @Test
    public void shouldReturnHasQueryStringParameter() {
        final Map<String, String> queryStringParams = new QueryStringParametersBuilder()
                .withPageNumber(1)
                .build();

        final Request<Object> request = BasicRequest.builder()
                .queryStringParameters(queryStringParams)
                .build();

        assertThat(request.hasQueryStringParameter("pageNumber")).isTrue();
        assertThat(request.hasQueryStringParameter("pageSize")).isFalse();
    }

    @Test
    public void shouldReturnQueryStringParameter() {
        final String pageNumber = "2";
        final Map<String, String> queryStringParams = new QueryStringParametersBuilder()
                .withPageNumber(Integer.parseInt(pageNumber))
                .build();

        final Request<Object> request = BasicRequest.builder()
                .queryStringParameters(queryStringParams)
                .build();

        assertThat(request.getQueryStringParameter("pageNumber")).isEqualTo(pageNumber);
    }

    @Test
    public void shouldReturnHasHeader() {
        final Map<String, String> headers = new HeadersBuilder()
                .withHost("localhost")
                .build();

        final Request<Object> request = BasicRequest.builder()
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

        final Request<Object> request = BasicRequest.builder()
                .headers(headers)
                .build();

        assertThat(request.getHeader("Host")).isEqualTo(host);
    }

    @Test
    public void shouldReturnHasPathParameter() {
        final Map<String, String> pathParams = new PathParametersBuilder()
                .withId(UUID.randomUUID())
                .build();

        final Request<Object> request = BasicRequest.builder()
                .pathParameters(pathParams)
                .build();

        assertThat(request.hasPathParameter("id")).isTrue();
        assertThat(request.hasPathParameter("key")).isFalse();
    }

    @Test
    public void shouldReturnPathParameter() {
        final UUID id = UUID.randomUUID();
        final Map<String, String> pathParams = new PathParametersBuilder()
                .withId(id)
                .build();

        final Request<Object> request = BasicRequest.builder()
                .pathParameters(pathParams)
                .build();

        assertThat(request.getPathParameter("id")).isEqualTo(id.toString());
    }

}
