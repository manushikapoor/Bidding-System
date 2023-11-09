package com.manushi.bidding.exceptions;

public class RabbitMqException extends RuntimeException {
    public RabbitMqException(String message) {
        super(message);
    }
}

