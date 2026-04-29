package com.prosa.workshop.rest.todo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
        return null; // TODO G: implement me
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
        return null; // TODO H: implement me
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
        return null; // TODO I: implement me
    }
}
