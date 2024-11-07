package org.katrin.glovo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserMessages {
    INVALID_EMAIL("Invalid email format."),
    INVALID_PHONE_NUMBER("Phone number must be in the format +<country code><10-digit number>."),
    INVALID_NAME_LENGTH("Name must can't be shorter than 3 characters."),
    INVALID_NAME("Name can contain only letters and hyphens."),
    INVALID_PASSWORD_LENGTH("Password must be between 6 and 20 characters."),
    INVALID_PASSWORD("Password must contain at least 1 uppercase and 1 lowercase letter, 1 symbol, and 1 digit.");

    private final String message;
}
