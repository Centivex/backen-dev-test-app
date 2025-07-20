package com.example.backend_dev_test_app.controllers;

import com.example.backend_dev_test_app.exceptions.ExternalServiceException;
import com.example.backend_dev_test_app.exceptions.NotFoundException;
import com.example.backend_dev_test_app.models.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.Map;

@RestControllerAdvice
public class HandleExceptionController {

    @RestControllerAdvice
    public class RestExceptionHandler {

        @ExceptionHandler(NotFoundException.class)
        public ResponseEntity<Error> handleNotFound(NotFoundException ex) {
            Error error = new Error();
            error.setMessage(ex.getMessage());
            error.setError("Not Found");
            error.setStatus(HttpStatus.NOT_FOUND.value());
            error.setDate(new Date());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        @ExceptionHandler(ExternalServiceException.class)
        public ResponseEntity<Error> handleExternalService(ExternalServiceException ex) {
            Error error = new Error();
            error.setMessage(ex.getMessage());
            error.setError("Bad Gateway");
            error.setStatus(HttpStatus.BAD_GATEWAY.value());
            error.setDate(new Date());
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(error);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<Error> handleGeneric(Exception ex) {
            Error error = new Error();
            error.setMessage("Unexpected error: " + ex.getMessage());
            error.setError("Internal Server Error");
            error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            error.setDate(new Date());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

}
