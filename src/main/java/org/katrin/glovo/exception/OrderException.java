package org.katrin.glovo.exception;

import java.io.IOException;

public class OrderException extends IOException {
    private final String message;

    public OrderException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
