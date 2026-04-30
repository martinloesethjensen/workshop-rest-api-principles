package com.prosa.workshop.rest.todo.dto;

import com.prosa.workshop.rest.todo.model.TodoStatus;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateTodoRequest {
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    private String title;

    @Size(max = 1000, message = "Description must be at most 1000 characters")
    private String description;

    private TodoStatus status;

    private LocalDateTime dueDate;
}
