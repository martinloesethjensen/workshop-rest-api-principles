package com.prosa.workshop.rest.todo.service;

import com.prosa.workshop.rest.todo.dto.CreateTodoRequest;
import com.prosa.workshop.rest.todo.dto.TodoDto;
import com.prosa.workshop.rest.todo.dto.UpdateTodoRequest;
import com.prosa.workshop.rest.todo.exception.ResourceNotFoundException;
import com.prosa.workshop.rest.todo.model.Todo;
import com.prosa.workshop.rest.todo.model.TodoStatus;
import com.prosa.workshop.rest.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public List<TodoDto> findAll(String status) {
        List<Todo> todos;
        if (status != null && !status.isEmpty()) {
            final TodoStatus todoStatus = TodoStatus.valueOf(status.toUpperCase());
            todos = todoRepository.findByStatus(todoStatus);
        } else {
            todos = todoRepository.findAll();
        }
        return todos.stream().map(this::toDto).toList();
    }

    public TodoDto findById(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> ResourceNotFoundException.forTodo(id));
        return toDto(todo);
    }

    @Transactional
    public TodoDto create(CreateTodoRequest request) {
        Todo todo = Todo.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(TodoStatus.OPEN)
                .dueDate(request.getDueDate())
                .build();
        return toDto(todoRepository.save(todo));
    }

    @Transactional
    public TodoDto update(Long id, UpdateTodoRequest request) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> ResourceNotFoundException.forTodo(id));
        if (request.getTitle() != null) todo.setTitle(request.getTitle());
        if (request.getDescription() != null) todo.setDescription(request.getDescription());
        if (request.getDueDate() != null) todo.setDueDate(request.getDueDate());
        if (request.getStatus() != null) todo.setStatus(request.getStatus());
        todo.setUpdatedAt(LocalDateTime.now());
        return toDto(todoRepository.save(todo));
    }

    @Transactional
    public TodoDto updateStatus(Long id, TodoStatus newStatus) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> ResourceNotFoundException.forTodo(id));
        if (todo.getStatus() != newStatus) {
            todo.setStatus(newStatus);
            todo.setUpdatedAt(LocalDateTime.now());
        }
        return toDto(todoRepository.save(todo));
    }

    @Transactional
    public void delete(Long id) {
        if (!todoRepository.existsById(id)) {
            throw ResourceNotFoundException.forTodo(id);
        }
        todoRepository.deleteById(id);
    }

    // -------------------------------------------------------------------------
    // Mapping helper — converts a Todo entity to a TodoDto.
    // You can use this in all methods above with: .map(this::toDto)
    // -------------------------------------------------------------------------
    private TodoDto toDto(Todo todo) {
        return TodoDto.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .status(todo.getStatus().name())
                .createdAt(todo.getCreatedAt())
                .updatedAt(todo.getUpdatedAt())
                .dueDate(todo.getDueDate())
                .build();
    }
}
