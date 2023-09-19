package com.example.todo.exceptions;

public class MismatchingPasswordsException extends RuntimeException{
    public MismatchingPasswordsException(){
        super("The passwords you introduced do not match.");
    }
}
