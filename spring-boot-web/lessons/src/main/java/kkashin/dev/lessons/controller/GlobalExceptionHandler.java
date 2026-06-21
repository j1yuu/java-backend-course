package kkashin.dev.lessons.controller;

import kkashin.dev.lessons.dto.ServerExceptionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ServerExceptionDto> handleValidationException(MethodArgumentNotValidException e) {
        log.error("Got validation exception", e);

        String detailedMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error ->
                    error.getField() + ": " + error.getDefaultMessage()
                )
                .collect(Collectors.joining(", "));

        var exception = new ServerExceptionDto(
                "Bad request. Validation failed",
                detailedMessage,
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ServerExceptionDto> handleNotFoundException(Exception e) {
        log.error("Got no such element exception", e);
        var exception = new ServerExceptionDto(
                "Element not found",
                e.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception);
    }

    @ExceptionHandler
    public ResponseEntity<ServerExceptionDto> handleGeneralException(Exception e) {
        log.error("Server error", e);
        var exception = new ServerExceptionDto(
                "Server error",
                e.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception);
    }
}

