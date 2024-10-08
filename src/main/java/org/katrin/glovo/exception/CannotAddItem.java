package org.katrin.glovo.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CannotAddItem extends RuntimeException {
    private String message;
}
