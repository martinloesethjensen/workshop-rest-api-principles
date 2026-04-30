package com.prosa.workshop.rest.todo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // -------------------------------------------------------------------------
    // TODO G — Handle ResourceNotFoundException
    // -------------------------------------------------------------------------
    // When a todo is not found, return 404 NOT_FOUND with an ErrorResponse.
    //
    // Hint:
    //   return ResponseEntity.status(HttpStatus.NOT_FOUND)
    //       .body(new ErrorResponse("NOT_FOUND", ex.getMessage()));
    // -------------------------------------------------------------------------
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        final HttpStatus status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(new ErrorResponse(status.name(), ex.getMessage()));
    }

    // -------------------------------------------------------------------------
    // TODO H — Handle MethodArgumentNotValidException
    // -------------------------------------------------------------------------
    // When @Valid fails on a request body, return 400 BAD_REQUEST with a
    // VALIDATION_ERROR response that lists all field errors in the message.
    //
    // Hint: ex.getBindingResult().getFieldErrors() gives you the list of errors.
    //       Each FieldError has .getField() and .getDefaultMessage().
    //       Join them: "title: Title is required, dueDate: must not be null"
    // -------------------------------------------------------------------------
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        List<String> errorFields = ex.getBindingResult().getFieldErrors()
                .stream()
                .map((field) -> String.format("%s: %s", field.getField(), field.getDefaultMessage()))
                .toList();

        final String errorMessage = String.join(", ", errorFields);

        log.error("Validation error", ex);
        final HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(new ErrorResponse("VALIDATION_ERROR", errorMessage));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        log.error("Invalid argument provided", ex);
        final HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status)
                .body(new ErrorResponse("INVALID_INPUT", ex.getMessage()));
    }

    // -------------------------------------------------------------------------
    // TODO I — Handle all other exceptions (catch-all)
    // -------------------------------------------------------------------------
    // For any unexpected error, return 500 INTERNAL_SERVER_ERROR.
    // IMPORTANT: log the full exception server-side, but return a safe generic
    // message to the client — never expose internal stack traces!
    //
    // Hint: add a Logger field:
    //   private static final org.slf4j.Logger log =
    //       org.slf4j.LoggerFactory.getLogger(GlobalExceptionHandler.class);
    //   log.error("Unexpected error", ex);
    // -------------------------------------------------------------------------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        log.error("Unexpected error", ex);
        final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status).body(new ErrorResponse(status.name(), "Unknown error"));
    }
}
