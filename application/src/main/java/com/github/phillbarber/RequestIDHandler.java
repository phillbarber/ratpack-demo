package com.github.phillbarber;

import org.slf4j.MDC;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.handling.RequestId;

public class RequestIDHandler implements Handler {

    public static final String REQUEST_ID = "Request-Id";

    @Override
    public void handle(Context context) throws Exception {
        RequestId requestId = context.get(RequestId.class);
        context.getResponse().getHeaders().add(REQUEST_ID, requestId);
        MDC.put(REQUEST_ID, requestId.toString());

        context.next();
    }
}
