package org.katrin.glovo.exception;

import java.io.IOException;

public class InsufficientStockException extends IOException {
    private final String message;

    public InsufficientStockException(String notEnoughStockAvailable) {
        this.message = notEnoughStockAvailable;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
