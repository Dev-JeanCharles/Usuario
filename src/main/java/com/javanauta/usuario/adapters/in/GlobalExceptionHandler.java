package com.javanauta.usuario.adapters.in;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javanauta.usuario.infrastructure.exceptions.*;
import com.javanauta.usuario.infrastructure.exceptions.IllegalArgumentException;
import com.javanauta.usuario.infrastructure.exceptions.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {
    private final ErrorResponseFactory errorFactory;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException exception, HttpServletRequest request) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorFactory.build(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                request.getRequestURI(),
                "Not Found"));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDTO> handleConflictException(ConflictException exception, HttpServletRequest request) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorFactory.build(
                HttpStatus.CONFLICT,
                exception.getMessage(),
                request.getRequestURI(),
                "Conflict"));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponseDTO> handleUnauthorizedException(UnauthorizedException exception, HttpServletRequest request) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorFactory.build(
                HttpStatus.UNAUTHORIZED,
                exception.getMessage(),
                request.getRequestURI(),
                "Unauthorized"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException exception, HttpServletRequest request) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorFactory.build(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request.getRequestURI(),
                "Bad Request"));
    }
}
