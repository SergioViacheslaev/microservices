package com.microservices.saga.productsservice.exception.handlers;

import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.EventMessageHandler;
import org.axonframework.eventhandling.ListenerInvocationErrorHandler;

/**
 *  Rethrows exception to make transaction rollback
 */
public class ProductsServiceEventsErrorHandler implements ListenerInvocationErrorHandler {

    @Override
    public void onError(Exception exception, EventMessage<?> event, EventMessageHandler eventHandler) throws Exception {
        throw exception;
    }

}
