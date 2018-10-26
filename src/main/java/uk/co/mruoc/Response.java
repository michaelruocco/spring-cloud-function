package uk.co.mruoc;

import java.util.Map;

public interface Response<I> {

    Map<String, String> getHeaders();

    I getBody();

    int getStatusCode();

}
