package com.deiz0n.makeorder.api.controllers.exceptions;

import com.deiz0n.makeorder.domain.services.exceptions.ExistingFieldException;
import com.deiz0n.makeorder.domain.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;

@ControllerAdvice
public class HandleController {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Error> handleResourceNotFoundException(ResourceNotFoundException exception, HttpServletRequest request) {
       var error = new Error(
               "Recurso não encontrado",
               exception.getMessage(),
               HttpStatus.NOT_FOUND,
               Instant.now(),
               request.getRequestURI()
       );
       return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
    }

    @ExceptionHandler(ExistingFieldException.class)
    public ResponseEntity<Error> handleExistingFieldException(ExistingFieldException exception, HttpServletRequest request) {
        var error = new Error(
                "Dado já cadastrado",
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                Instant.now(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(error);
    }

}
