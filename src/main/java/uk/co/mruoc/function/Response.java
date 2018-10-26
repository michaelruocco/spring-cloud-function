package uk.co.mruoc.function;

import java.util.Map;

public interface Response<I> {

    Map<String, String> getHeaders();

    I getBody();

    int getStatusCode();

}
