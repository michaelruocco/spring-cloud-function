package uk.co.mruoc.function;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent.ProxyRequestContext;

public class ProxyRequestContextBuilder {

    private final ProxyRequestContext context = new ProxyRequestContext();

    public ProxyRequestContextBuilder withStage(String stage) {
        context.setStage(stage);
        return this;
    }

    public ProxyRequestContext build() {
        return context;
    }

}
