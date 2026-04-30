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

    @GetMapping
    public ResponseEntity<List<TodoDto>> getAllTodos(@RequestParam(required = false) String status) {
        final List<TodoDto> result = todoService.findAll(status);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getTodoById(@PathVariable Long id) {
        final TodoDto result = todoService.findById(id);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<TodoDto> createTodo(
            @Valid @RequestBody CreateTodoRequest request,
            UriComponentsBuilder uriBuilder
    ) {
        final TodoDto created = todoService.create(request);
        var location = uriBuilder.path("/api/v1/todos/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> updateTodo(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTodoRequest request
    ) {
        final TodoDto result = todoService.update(id, request);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TodoDto> updateStatus(
            @PathVariable Long id,
            @RequestParam TodoStatus status
    ) {
        final TodoDto result = todoService.updateStatus(id, status);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
