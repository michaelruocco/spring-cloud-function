package uk.co.mruoc;

import java.util.UUID;

public class ResourceUriBuilder {

    private ResourceUriBuilder() {
        // utility class
    }

    public static String build(String baseUri, UUID id) {
        return String.format("%s/%s", baseUri, id);
    }

}
