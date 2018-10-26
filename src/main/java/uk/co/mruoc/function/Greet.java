package uk.co.mruoc.function;

import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.AbstractFunction;
import uk.co.mruoc.DefaultResponse;
import uk.co.mruoc.Input;
import uk.co.mruoc.Output;
import uk.co.mruoc.Request;
import uk.co.mruoc.Response;

@Slf4j
public class Greet extends AbstractFunction<Input, Output> {

    public Greet() {
        super(Input.class);
    }

    @Override
    public Response<Output> apply(Request<Input> request) {
        Input input = request.getBody();
        Output output = new Output("Hello " + input.getValue() + ", and welcome to Spring Cloud Function!!!");
        return toResponse(output);
    }

    private Response<Output> toResponse(Output body) {
        return DefaultResponse.<Output>builder()
                .statusCode(200)
                .body(body)
                .build();
    }

}