package com.example.todo.services;

import com.example.todo.entities.TodoEntity;
import com.example.todo.entities.UserEntity;
import com.example.todo.exceptions.ForbiddenActionException;
import com.example.todo.exceptions.NotFoundException;
import com.example.todo.models.Todos.TodoDTO;
import com.example.todo.repositories.TodoRepository;
import com.example.todo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    public TodoDTO createTodo (TodoDTO todo, long userId) {
        this.checkPermission(userId);

        Optional<UserEntity> userEntityOpt = this.userRepository.findById(userId);

        if(userEntityOpt.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        TodoEntity todoEntity = new TodoEntity(todo.getTask(), todo.getDeadline(), todo.getStatus(), userEntityOpt.get());
        todoRepository.save(todoEntity);

        return new TodoDTO(todoEntity);
    }

    public List<TodoDTO> getAllTodos () {
        List<TodoEntity> todoEntities = this.todoRepository.findAll();
        List<TodoDTO> todoDTOS = new ArrayList<>();

        for(TodoEntity todoEntity : todoEntities) {
            todoDTOS.add(new TodoDTO(todoEntity));
        }

        return todoDTOS;
    };

    public List<TodoDTO> getTodosByUser (long userId) {
        this.checkPermission(userId);
        List<TodoEntity> todoEntities = this.todoRepository.findAll();
        List<TodoDTO> todoDTOS = new ArrayList<>();

        for(TodoEntity todoEntity : todoEntities) {
            if(todoEntity.getUserEntity().getId() == userId) {
                todoDTOS.add(new TodoDTO(todoEntity));
            }
        }

        return todoDTOS;
    };

    public TodoDTO editTodo (TodoDTO todo, long userId, long todoId) {
        this.checkPermission(userId);

        Optional<TodoEntity> todoEntityOpt = this.todoRepository.findById(todoId);

        if(todoEntityOpt.isEmpty()) {
            throw new NotFoundException("Todo with id = " + todoId + " has not been found");
        }

        TodoEntity todoEntity = todoEntityOpt.get();

        todoEntity.setTask(todo.getTask());
        todoEntity.setStatus(todo.getStatus());
        todoEntity.setDeadline(todo.getDeadline());

        todoRepository.save(todoEntity);

        return new TodoDTO(todoEntity);
    }

    public boolean deleteTodo (long userId, long todoId) {
        this.checkPermission(userId);
        this.todoRepository.deleteById(todoId);
        return true;
    };

    public void checkPermission (long requestUserId) {
        Optional<UserEntity> userEntityOpt = this.userRepository.findById(requestUserId);
        if(userEntityOpt.isEmpty()) {
            throw new NotFoundException("User with id = " + requestUserId + " not found");
        }

        String usernameFromDb = userEntityOpt.get().getUsername();
        String usernameByContext = SecurityContextHolder.getContext().getAuthentication().getName();

        if(!usernameByContext.equals(usernameFromDb)) {
            throw new ForbiddenActionException("You are not allowed to do this action");
        }
    }
}
