package com.example.todo.exceptions;

public class AlreadyExistingException extends RuntimeException {
    public AlreadyExistingException(String msg) {
        super(msg);
    }
}
