package org.katrin.glovo.advicer;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex) {
        // Получаем все сообщения об ошибках
        List<String> errorMessages = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessageTemplate) // Получаем только messageTemplate
                .collect(Collectors.toList());

        // Если только одно сообщение, возвращаем его как строку
        if (errorMessages.size() == 1) {
            return new ResponseEntity<>(errorMessages.get(0), HttpStatus.BAD_REQUEST);
        }
        // Если несколько, возвращаем список
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }




    // Другие обработчики исключений
}

