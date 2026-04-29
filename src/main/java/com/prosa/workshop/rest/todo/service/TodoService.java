package com.prosa.workshop.rest.todo.service;

import com.prosa.workshop.rest.todo.dto.CreateTodoRequest;
import com.prosa.workshop.rest.todo.dto.TodoDto;
import com.prosa.workshop.rest.todo.dto.UpdateTodoRequest;
import com.prosa.workshop.rest.todo.model.Todo;
import com.prosa.workshop.rest.todo.model.TodoStatus;
import com.prosa.workshop.rest.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    // -------------------------------------------------------------------------
    // TODO 1 — findAll
    // -------------------------------------------------------------------------
    // If `status` is not null, filter todos by that status.
    // Otherwise return all todos.
    // Map each Todo entity to a TodoDto using toDto().
    //
    // Hint: TodoStatus.valueOf(status.toUpperCase()) converts "open" → TodoStatus.OPEN
    // Hint: todoRepository.findByStatus(TodoStatus) and todoRepository.findAll()
    // -------------------------------------------------------------------------
    public List<TodoDto> findAll(String status) {
        throw new UnsupportedOperationException("TODO 1: implement findAll");
    }

    // -------------------------------------------------------------------------
    // TODO 2 — findById
    // -------------------------------------------------------------------------
    // Find a Todo by id. If not found, throw ResourceNotFoundException.forTodo(id).
    // Return the result mapped to a TodoDto.
    //
    // Hint: todoRepository.findById(id) returns an Optional<Todo>
    // Hint: optional.map(this::toDto).orElseThrow(...)
    // -------------------------------------------------------------------------
    public TodoDto findById(Long id) {
        throw new UnsupportedOperationException("TODO 2: implement findById");
    }

    // -------------------------------------------------------------------------
    // TODO 3 — create
    // -------------------------------------------------------------------------
    // Build a new Todo entity from the request, save it, and return the DTO.
    //
    // Hint: Todo.builder().title(...).description(...).build()
    // Hint: todoRepository.save(todo) returns the saved entity (with generated id)
    // -------------------------------------------------------------------------
    @Transactional
    public TodoDto create(CreateTodoRequest request) {
        throw new UnsupportedOperationException("TODO 3: implement create");
    }

    // -------------------------------------------------------------------------
    // TODO 4 — update
    // -------------------------------------------------------------------------
    // Find the todo by id (throw 404 if missing). Apply non-null fields from
    // the request. Save and return the updated DTO.
    //
    // Hint: only overwrite a field if the request value is not null
    // Hint: JPA tracks changes automatically inside a @Transactional method —
    //       you don't need to call save() if you loaded the entity from the repo,
    //       but calling save() explicitly is fine and makes intent clear.
    // -------------------------------------------------------------------------
    @Transactional
    public TodoDto update(Long id, UpdateTodoRequest request) {
        throw new UnsupportedOperationException("TODO 4: implement update");
    }

    // -------------------------------------------------------------------------
    // TODO 5 — updateStatus
    // -------------------------------------------------------------------------
    // Find the todo by id (throw 404 if missing). Set its status. Return DTO.
    // -------------------------------------------------------------------------
    @Transactional
    public TodoDto updateStatus(Long id, TodoStatus newStatus) {
        throw new UnsupportedOperationException("TODO 5: implement updateStatus");
    }

    // -------------------------------------------------------------------------
    // TODO 6 — delete
    // -------------------------------------------------------------------------
    // Find the todo by id (throw 404 if missing). Then delete it.
    //
    // Hint: todoRepository.delete(todo) or todoRepository.deleteById(id)
    //       But throw 404 first if the todo doesn't exist!
    // -------------------------------------------------------------------------
    @Transactional
    public void delete(Long id) {
        throw new UnsupportedOperationException("TODO 6: implement delete");
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
                .dueDate(todo.getDueDate())
                .build();
    }
}
