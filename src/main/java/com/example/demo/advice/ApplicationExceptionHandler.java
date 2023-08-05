package com.example.demo.advice;

import com.example.demo.exception.UserException;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@CrossOrigin
public class ApplicationExceptionHandler {
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(UserException.class)
//    public Map<String, String> handleBusinessException(UserException ex) {
//        Map<String, String> errorMap = new HashMap<>();
//        errorMap.put("errorMessage", ex.getMessage());
//        return errorMap;
//    }
@ExceptionHandler(UserException.class)
public ResponseEntity<Map<String, String>> handleBusinessException(UserException ex) {
    Map<String, String> errorMap = new HashMap<>();
    errorMap.put("errorMessage", ex.getMessage());
    // You can add more details to the error response if needed
    // errorMap.put("errorCode", "123");

    // Customize the HTTP status code here (e.g., BAD_REQUEST, FORBIDDEN, etc.)
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    return new ResponseEntity<>(errorMap, status);
}

//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(AuthenticationException.class)
//    public Map<String, String> handleauthexception(AuthenticationException ex) {
//        Map<String, String> errorMap = new HashMap<>();
//        ex.getBindingResult().getFieldErrors().forEach(error -> {
//            errorMap.put(error.getField(), error.getDefaultMessage());
//        });
//        return errorMap;
//    }

}
