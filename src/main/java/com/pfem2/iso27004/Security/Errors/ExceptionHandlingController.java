package com.pfem2.iso27004.Security.Errors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(BadrequestException.class)
    public ResponseEntity<String> handleEmailAlreadyExistsException(BadrequestException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    // Add other exception handlers as needed...
}
