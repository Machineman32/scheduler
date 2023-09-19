package com.example.todo.exceptions;

public class ForbiddenActionException extends RuntimeException {
    public ForbiddenActionException (String msg) {
        super(msg);
    }
}
