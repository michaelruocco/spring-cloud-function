package uk.co.mruoc.function;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.api.Input;
import uk.co.mruoc.api.Output;

@Slf4j
public class Reverse extends AbstractAwsLambdaFunction<Input, Output> {

    public Reverse() {
        super(Input.class);
    }

    @Override
    public Response<Output> apply(Request<Input> request) {
        Input input = request.getBody();
        Output output = new Output(new StringBuilder(input.getValue()).reverse().toString());
        return toResponse(output);
    }

    private Response<Output> toResponse(Output body) {
        return BasicResponse.<Output>builder()
                .statusCode(200)
                .body(body)
                .build();
    }

}
