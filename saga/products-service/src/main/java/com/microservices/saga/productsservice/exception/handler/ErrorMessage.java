package com.microservices.saga.productsservice.exception.handler;

import java.util.Date;

public record ErrorMessage(Date timestamp, String message) {

}
