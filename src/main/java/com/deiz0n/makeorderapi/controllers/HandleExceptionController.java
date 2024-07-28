package com.deiz0n.makeorderapi.controllers;

import com.deiz0n.makeorderapi.domain.exceptions.*;
import com.deiz0n.makeorderapi.domain.utils.responses.ErrorResponse;
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
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.Instant;

@ControllerAdvice
public class HandleExceptionController extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException exception, HttpServletRequest request) {
        var error = new ErrorResponse(
                Instant.now(),
                "Recurso não encontrado",
                exception.getMessage(),
                HttpStatus.NOT_FOUND,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({ResourceExistingException.class})
    public ResponseEntity<ErrorResponse> handleResourceExistingExcepion(ResourceExistingException exception, HttpServletRequest request) {
        var error = new ErrorResponse(
                Instant.now(),
                "Recurso existente",
                exception.getMessage(),
                HttpStatus.CONFLICT,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ResourceIsEmptyException.class})
    public ResponseEntity<ErrorResponse> handleResourceIsEmptyException(ResourceIsEmptyException exception, HttpServletRequest request) {
        var error = new ErrorResponse(
                Instant.now(),
                "Campo vazio",
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var error = new ErrorResponse(
                Instant.now(),
                "Campo inválido",
                ex.getFieldError().getDefaultMessage(),
                HttpStatus.BAD_REQUEST,
                request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        var detail = new StringBuilder();

        if (ex.getCause().toString().contains("Setor"))
            detail.append("Os cargos disponíveis são: [GARCOM, COZINHA, ADMINISTRACAO]");
        if (ex.getCause().toString().contains("FormaPagamento"))
            detail.append("As formas de pagamentos disponíveis são: [PIX, DINHEIRO, CREDITO, DEBITO]");
        if (ex.getCause().toString().contains("StatusPedido"))
            detail.append("Os status do pedido são: [PENDENTE, CONFIRMADO, CONCLUIDO]");
        if (detail.isEmpty())
            detail.append("O JSON informado possui formato inválido");

        var error = new ErrorResponse(
                Instant.now(),
                "Formato inválido",
                detail.toString(),
                HttpStatus.BAD_REQUEST,
                request.getDescription(false)
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var error = new ErrorResponse(
                Instant.now(),
                "Não encontrado",
                "Recurso inexistente",
                HttpStatus.NOT_FOUND,
                request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var error = new ErrorResponse(
                Instant.now(),
                "Recurso não aceito",
                "Os formatos aceitos são: [application/json, application/*+json].",
                HttpStatus.valueOf(status.value()),
                request.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(error);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<?> handlerAuthenticationException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({InsufficientAuthenticationException.class})
    public ResponseEntity<ErrorResponse> handleInsufficientAuthenticationException(HttpServletRequest request) {
        var error = new ErrorResponse(
                Instant.now(),
                "Token inválido",
                "Token inválido, expirado ou nulo",
                HttpStatus.UNAUTHORIZED,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({BadCredentialsException.class, InternalAuthenticationServiceException.class})
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(HttpServletRequest request) {
        var error = new ErrorResponse(
                Instant.now(),
                "Credenciais inválidas",
                "Email ou senha inválido(a)",
                HttpStatus.UNAUTHORIZED,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(HttpServletRequest request) {
        var error = new ErrorResponse(
                Instant.now(),
                "Acesso negado",
                "O funcionário não tem permissão para acessar tal recurso",
                HttpStatus.FORBIDDEN,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler({SendEmailException.class})
    public ResponseEntity<ErrorResponse> handleSendEmailException(SendEmailException exception, HttpServletRequest request) {
        var error = new ErrorResponse(
                Instant.now(),
                "Erro no servidor interno",
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({PasswordNotEqualsException.class})
    public ResponseEntity<ErrorResponse> handlePasswordNotEqualsException(PasswordNotEqualsException exception, HttpServletRequest request) {
        var error = new ErrorResponse(
                Instant.now(),
                "Erro ao alterar a senha",
                exception.getMessage(),
                HttpStatus.UNAUTHORIZED,
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
}
