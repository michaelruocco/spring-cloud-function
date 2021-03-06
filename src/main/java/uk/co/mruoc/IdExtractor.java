package uk.co.mruoc;

import uk.co.mruoc.PathParameterProvider;
import uk.co.mruoc.function.IdNotProvidedException;

import java.util.Map;
import java.util.UUID;

public class IdExtractor {

    public UUID extract(PathParameterProvider request) {
        Map<String, String> pathParameters = request.getPathParameters();
        if (pathParameters.containsKey("id")) {
            return UUID.fromString(pathParameters.get("id"));
        }
        throw new IdNotProvidedException();
    }

}
