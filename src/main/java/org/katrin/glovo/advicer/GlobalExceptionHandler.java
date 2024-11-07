package org.katrin.glovo.advicer;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.katrin.glovo.exception.OrderException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Обробка помилок унікальності в базі даних
//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
//        String errorMessage = ex.getMostSpecificCause().getMessage();
//        if (errorMessage.contains("products_name_country_key"))
//            return new ResponseEntity<>("A product with this name and country already exists.", HttpStatus.BAD_REQUEST);
//        else
//            return new ResponseEntity<>("Uniqueness error: " + errorMessage, HttpStatus.BAD_REQUEST);
//    }

    // Обробка виключень, пов'язаних з обмеженнями полів в базі даних
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex) {
        // Отримуємо всі повідомлення про помилки
        List<String> errorMessages = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        if (errorMessages.size() == 1)
            return new ResponseEntity<>(errorMessages.getFirst(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

//    // Обробка виключень, пов'язаних з замовленнями
//    @ExceptionHandler(OrderException.class)
//    public ResponseEntity<?> handleProductException(OrderException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//    }
//
//    // Обробка виключень, пов'язаних з не існуванням запису в базі даних
//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
   // }
}
