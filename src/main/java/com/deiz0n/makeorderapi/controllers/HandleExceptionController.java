package com.deiz0n.makeorderapi.controllers;

import com.deiz0n.makeorderapi.domain.exceptions.DataIntegrityException;
import com.deiz0n.makeorderapi.domain.exceptions.ResourceExistingException;
import com.deiz0n.makeorderapi.domain.exceptions.ResourceNotFoundException;
import com.deiz0n.makeorderapi.domain.utils.Error;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class HandleExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Error> handleResourceNotFound(ResourceNotFoundException exception, HttpServletRequest request) {
        var error = new Error(
                Instant.now(),
                "Recurso não encontrado",
                exception.getMessage(),
                HttpStatus.NOT_FOUND,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler({ResourceExistingException.class})
    public ResponseEntity<Error> handleResourceExistingExcepion(ResourceExistingException exception, HttpServletRequest request) {
        var error = new Error(
                Instant.now(),
                "Recurdo existente",
                exception.getMessage(),
                HttpStatus.CONFLICT,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var error = new Error(
                Instant.now(),
                "Campo inválido",
                ex.getFieldError().getDefaultMessage(),
                HttpStatus.BAD_REQUEST,
                request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler({DataIntegrityException.class})
    public ResponseEntity<Error> handleDataIntegrityException(DataIntegrityException exception, HttpServletRequest request) {
        var error = new Error(
                Instant.now(),
                "Recurdo em uso",
                exception.getMessage(),
                HttpStatus.CONFLICT,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}
