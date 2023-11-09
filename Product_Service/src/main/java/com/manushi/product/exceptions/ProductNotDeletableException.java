package com.manushi.product.exceptions;

public class ProductNotDeletableException extends RuntimeException {
    public ProductNotDeletableException(String message) {
        super(message);
    }
}

