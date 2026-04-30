package com.prosa.workshop.rest.todo.controller;

import com.prosa.workshop.rest.todo.dto.CreateTodoRequest;
import com.prosa.workshop.rest.todo.dto.TodoDto;
import com.prosa.workshop.rest.todo.dto.UpdateTodoRequest;
import com.prosa.workshop.rest.todo.model.TodoStatus;
import com.prosa.workshop.rest.todo.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    // -------------------------------------------------------------------------
    // TODO A — GET /api/v1/todos
    // -------------------------------------------------------------------------
    // Return all todos. Support optional filtering via ?status=OPEN|IN_PROGRESS|DONE
    // Response: 200 OK with a list of TodoDto (empty list [] if none found)
    // -------------------------------------------------------------------------
    @GetMapping
    public ResponseEntity<List<TodoDto>> getAllTodos(@RequestParam(required = false) String status) {
        final List<TodoDto> result = todoService.findAll(status);
        return ResponseEntity.ok().body(result);
    }

    // -------------------------------------------------------------------------
    // TODO B — GET /api/v1/todos/{id}
    // -------------------------------------------------------------------------
    // Return a single todo by its id.
    // Response: 200 OK with the TodoDto, or 404 if not found (handled globally)
    // -------------------------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getTodoById(@PathVariable Long id) {
        final TodoDto result = todoService.findById(id);
        return ResponseEntity.ok(result);
    }

    // -------------------------------------------------------------------------
    // TODO C — POST /api/v1/todos
    // -------------------------------------------------------------------------
    // Create a new todo from the request body.
    // Response: 201 Created with the new TodoDto AND a Location header
    //           pointing to the new resource: /api/v1/todos/{id}
    // -------------------------------------------------------------------------
    @PostMapping
    public ResponseEntity<TodoDto> createTodo(
            @Valid @RequestBody CreateTodoRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        return null; // TODO C: implement me
    }

    // -------------------------------------------------------------------------
    // TODO D — PUT /api/v1/todos/{id}
    // -------------------------------------------------------------------------
    // Full update of a todo (replace non-null fields from request body).
    // Response: 200 OK with updated TodoDto, or 404 if not found
    // -------------------------------------------------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> updateTodo(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTodoRequest request
    ) {
        return null; // TODO D: implement me
    }

    // -------------------------------------------------------------------------
    // TODO E — PATCH /api/v1/todos/{id}/status
    // -------------------------------------------------------------------------
    // Update the status of a todo only.
    // Example: PATCH /api/v1/todos/1/status?status=IN_PROGRESS
    // Response: 200 OK with updated TodoDto, or 404 if not found
    // -------------------------------------------------------------------------
    @PatchMapping("/{id}/status")
    public ResponseEntity<TodoDto> updateStatus(
            @PathVariable Long id,
            @RequestParam TodoStatus status
    ) {
        return null; // TODO E: implement me
    }

    // -------------------------------------------------------------------------
    // TODO F — DELETE /api/v1/todos/{id}
    // -------------------------------------------------------------------------
    // Delete a todo by id.
    // Response: 204 No Content (no body), or 404 if not found
    // -------------------------------------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        return null; // TODO F: implement me
    }
}
