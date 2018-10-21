package uk.co.mruoc.function;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import uk.co.mruoc.Request;
import uk.co.mruoc.Response;

import java.util.function.Function;

@Slf4j
public class Reverse implements Function<Message<Request>, Message<Response>> {

    @Override
    public Message<Response> apply(Message<Request> message) {
        log.info("got message {}", message);
        Request request = message.getPayload();
        log.info("got request {}", request);
        return new GenericMessage<>(new Response(new StringBuilder(request.getValue()).reverse().toString()));
    }

}
