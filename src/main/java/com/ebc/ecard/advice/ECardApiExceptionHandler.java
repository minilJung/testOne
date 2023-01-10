package com.ebc.ecard.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
class ECardApiExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<HttpExceptionResponseDto> handleNotFoundStatus(NoHandlerFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                    new HttpExceptionResponseDto(
                    Instant.now().toString(),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    exception.getMessage().replaceAll(
                        " /api.*",
                        ""
                    )
                ));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<HttpExceptionResponseDto> handleAccessDeniedStatus(AccessDeniedException exception) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(
                    new HttpExceptionResponseDto(
                    Instant.now().toString(),
                    HttpStatus.FORBIDDEN.value(),
                    HttpStatus.FORBIDDEN.getReasonPhrase(),
                    exception.getMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpExceptionResponseDto> handleAccessDeniedStatus(HttpServletRequest request, Exception exception) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(
                    new HttpExceptionResponseDto(
                    Instant.now().toString(),
                    HttpStatus.FORBIDDEN.value(),
                    HttpStatus.FORBIDDEN.getReasonPhrase(),
                    exception.getMessage()
                ));
    }
}