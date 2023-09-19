package com.example.todo.models.Todos;

import com.example.todo.entities.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class TodoDTO {
    private String task;
    private LocalDate deadline;
    private STATUS status;

    public TodoDTO (TodoEntity todoEntity) {
        this.task = todoEntity.getTask();
        this.deadline = todoEntity.getDeadline();
        this.status = todoEntity.getStatus();
    }
}
