package com.wut.directeur.rest.endpoints;

import com.wut.directeur.rest.dtos.EndpointResponse;
import com.wut.directeur.rest.exception.DepartmentNotFoundException;
import com.wut.directeur.rest.exception.EmployeeNotFoundException;
import com.wut.directeur.rest.exception.PositionNotFoundException;
import com.wut.directeur.rest.exception.RoleNotFoundException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandlers {

    private static final String APPLICATION_JSON = "application/json";

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<EndpointResponse> handleEmployeeNotFoundException(EmployeeNotFoundException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
        return new ResponseEntity<>(
                new EndpointResponse("error", "employee with requested id does not exist"),
                headers,
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(PositionNotFoundException.class)
    public ResponseEntity<EndpointResponse> handlePositionNotFoundException(PositionNotFoundException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
        return new ResponseEntity<>(
                new EndpointResponse("error", "position with requested id does not exist"),
                headers,
                HttpStatus.NOT_FOUND
        );
    }


    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<EndpointResponse> handleEmployeeNotFoundException(DepartmentNotFoundException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
        return new ResponseEntity<>(
                new EndpointResponse("error", "department with requested id does not exist"),
                headers,
                HttpStatus.NOT_FOUND
        );
    }


    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<EndpointResponse> handleRoleNotFoundException(RoleNotFoundException ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON);
        return new ResponseEntity<>(
                new EndpointResponse("error", "role with requested id does not exist"),
                headers,
                HttpStatus.NOT_FOUND
        );
    }
}