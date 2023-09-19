package com.example.todo.controllers;

import com.example.todo.models.Todos.TodoDTO;
import com.example.todo.services.TodoService;
import com.example.todo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {
    @Autowired
    private TodoService todoService;

    @PostMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<TodoDTO> createTodo (@RequestBody TodoDTO todo, @PathVariable long userId) {
        return ResponseEntity.ok(this.todoService.createTodo(todo, userId));
    }

    @GetMapping("/admin") //test purpose
    public ResponseEntity<List<TodoDTO>> getAllTodos () {
        return ResponseEntity.ok(this.todoService.getAllTodos());
    }

    @GetMapping("{userId}")
    public ResponseEntity<List<TodoDTO>> getTodosByUser (@PathVariable long userId) {
        return ResponseEntity.ok(this.todoService.getTodosByUser(userId));
    }

    @PutMapping("/{userId}/{todoId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<TodoDTO> editTodo (@RequestBody TodoDTO todo, @PathVariable long userId, @PathVariable long todoId) {
        return ResponseEntity.ok(this.todoService.editTodo(todo, userId, todoId));
    }

    @DeleteMapping("/{userId}/{todoId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Boolean> deleteTodo (@PathVariable long userId, @PathVariable long todoId) {
        return ResponseEntity.ok(this.todoService.deleteTodo(userId, todoId));
    }
}
