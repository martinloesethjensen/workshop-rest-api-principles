package com.prosa.workshop.rest.todo.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException forTodo(Long id) {
        return new ResourceNotFoundException("Todo not found with id: " + id);
    }
}
