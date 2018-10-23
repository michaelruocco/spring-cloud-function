package uk.co.mruoc.function;

import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.Request;
import uk.co.mruoc.Response;

import java.util.function.Function;

@Slf4j
public class Reverse implements Function<Request, Response> {

    @Override
    public Response apply(Request request) {
        log.info("got request {}", request);
        return new Response(new StringBuilder(request.getValue()).reverse().toString());
    }

}
