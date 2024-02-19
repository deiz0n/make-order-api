package com.deiz0n.makeorderapi.api.controllers.exceptions;

import com.deiz0n.makeorderapi.domain.services.exceptions.ExistingFieldException;
import com.deiz0n.makeorderapi.domain.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Error> handleAccessDeniedException(AccessDeniedException exception, HttpServletRequest request) {
        var error = new Error(
                "Acesso negado",
                "O usuário não possui permissão para realizar tal ação",
                HttpStatus.FORBIDDEN,
                Instant.now(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleDataNotValidate(MethodArgumentNotValidException exception, HttpServletRequest request) {
        var error = new Error(
                "Campo inválido",
                exception.getFieldError().getDefaultMessage(),
                HttpStatus.BAD_REQUEST,
                Instant.now(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(error);
    }

}
