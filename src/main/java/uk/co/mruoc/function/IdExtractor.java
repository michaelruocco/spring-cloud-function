package uk.co.mruoc.function;

import uk.co.mruoc.Request;

import java.util.Map;
import java.util.UUID;

public class IdExtractor {

    public UUID extract(Request<Object> request) {
        Map<String, String> pathParameters = request.getPathParameters();
        if (pathParameters.containsKey("id")) {
            return UUID.fromString(pathParameters.get("id"));
        }
        throw new IdNotProvidedException();
    }

}
