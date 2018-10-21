package uk.co.mruoc.function;

import uk.co.mruoc.Response;

import java.util.function.Function;

public class Greet implements Function<Request, Response> {
 
    @Override
    public Response apply(Request request) {
        String input = request.getValue();
        return new Response("Hello " + input + ", and welcome to Spring Cloud Function!!!");
    }

}