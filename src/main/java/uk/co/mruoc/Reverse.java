package uk.co.mruoc;

import uk.co.mruoc.function.Request;

import java.util.function.Function;

public class Reverse implements Function<Request, Response> {

    @Override
    public Response apply(Request request) {
        String input = request.getValue();
        return new Response(new StringBuilder(input).reverse().toString());
    }

}
