package org.katrin.glovo.advicer;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.katrin.glovo.exception.OrderException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Обробка виключень, пов'язаних з обмеженнями полів в базі даних
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex) {
        // Отримуємо всі повідомлення про помилки
        List<String> errorMessages = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessageTemplate) // Отримуємо тільки повідомлення, яке сами створювали
                .collect(Collectors.toList());

        // Якщо тільки одне повідомлення, повертаємо його як рядок
        if (errorMessages.size() == 1)
            return new ResponseEntity<>(errorMessages.get(0), HttpStatus.BAD_REQUEST);

        // Якщо кілька, повертаємо список
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

    // Обробка виключень, пов'язаних з замовленнями
    @ExceptionHandler(OrderException.class)
    public ResponseEntity<?> handleProductException(OrderException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Обробка виключень, пов'язаних з не існуванням запису в базі даних
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
