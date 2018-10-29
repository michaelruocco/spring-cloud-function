package uk.co.mruoc;

import java.util.Map;

public interface PathParameterProvider {

    Map<String, String> getPathParameters();

    boolean hasPathParameter(String name);

    String getPathParameter(String name);

}
