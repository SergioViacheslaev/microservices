package com.microservices.saga.productsservice.exception.handlers;

import java.util.Date;

public record ErrorMessage(Date timestamp, String message) {

}
