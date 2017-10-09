package com.braintri.directeur.rest.endpoints;

import com.braintri.directeur.rest.exception.EmployeeNotFoundException;
import com.braintri.directeur.rest.exception.PositionNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandlers {

    private static final String APPLICATION_JSON = "application/json";

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<String> handleEmployeeNotFoundException(EmployeeNotFoundException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
        return new ResponseEntity<>(
                "{\"error\": employee with requested id does not exist\"\"}",
                headers,
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(PositionNotFoundException.class)
    public ResponseEntity<String> handlePositionNotFoundException(PositionNotFoundException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
        return new ResponseEntity<>(
                "{\"error\": position with requested id does not exist\"\"}",
                headers,
                HttpStatus.NOT_FOUND
        );
    }
}