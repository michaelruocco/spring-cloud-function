package uk.co.mruoc;

import java.util.Map;

public interface Request<I> {

    Map<String, String> getHeaders();

    I getBody();

    Map<String, String> getPathParameters();

    Map<String, String> getQueryStringParameters();

}
