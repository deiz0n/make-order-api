package com.deiz0n.makeorderapi.controllers;

import com.deiz0n.makeorderapi.domain.exceptions.*;
import com.deiz0n.makeorderapi.domain.utils.Error;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
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

    @ExceptionHandler({ResourceIsEmptyException.class})
    public ResponseEntity<Error> handleResourceIsEmptyException(ResourceIsEmptyException exception, HttpServletRequest request) {
        var error = new Error(
                Instant.now(),
                "Campo vazio",
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
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

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var error = new Error(
                Instant.now(),
                "Formato inválido",
                "O JSON informado possui formato inválido",
                HttpStatus.BAD_REQUEST,
                request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<?> handlerAuthenticationException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ExceptionHandler({InsufficientAuthenticationException.class})
    public ResponseEntity<Error> handleInsufficientAuthenticationException(HttpServletRequest request) {
        var error = new Error(
                Instant.now(),
                "Token inválido",
                "Token inválido, expirado ou nulo",
                HttpStatus.UNAUTHORIZED,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler({BadCredentialsException.class, InternalAuthenticationServiceException.class})
    public ResponseEntity<Error> handleBadCredentialsException(HttpServletRequest request) {
        var error = new Error(
                Instant.now(),
                "Credenciais inválidas",
                "Email ou senha inválido(a)",
                HttpStatus.UNAUTHORIZED,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<Error> handleAccessDeniedException(HttpServletRequest request) {
        var error = new Error(
                Instant.now(),
                "Acesso negado",
                "O funcionário não tem permissão para acessar tal recurso",
                HttpStatus.FORBIDDEN,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler({GenerateCodeException.class})
    public ResponseEntity<Error> handleGenerateCodeException(GenerateCodeException exception, HttpServletRequest request) {
        var error = new Error(
                Instant.now(),
                "Erro no servidor interno",
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler({SendEmailException.class})
    public ResponseEntity<Error> handleSendEmailException(SendEmailException exception, HttpServletRequest request) {
        var error = new Error(
                Instant.now(),
                "Erro no servidor interno",
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
