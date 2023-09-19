package com.example.todo.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> notFound (NotFoundException notFoundException, HttpServletRequest request){
        return buildApiError(HttpStatus.NOT_FOUND, notFoundException, request);
    }

    @ExceptionHandler(AlreadyExistingException.class)
    public ResponseEntity<ApiError> alreadyExisting (AlreadyExistingException alreadyExisting, HttpServletRequest request) {
        return this.buildApiError(HttpStatus.CONFLICT, alreadyExisting, request);
    }

    @ExceptionHandler(MismatchingPasswordsException.class)
    public ResponseEntity<ApiError> mismatchingPasswords(MismatchingPasswordsException mismatchingPasswordsException, HttpServletRequest request){
        return buildApiError(HttpStatus.BAD_REQUEST, mismatchingPasswordsException, request);
    }

    @ExceptionHandler(ForbiddenActionException.class)
    public ResponseEntity<ApiError> forbiddenAction(ForbiddenActionException forbiddenActionException, HttpServletRequest request) {
        return buildApiError(HttpStatus.FORBIDDEN, forbiddenActionException, request);
    }

    private ResponseEntity<ApiError> buildApiError(HttpStatus httpStatus, RuntimeException exception, HttpServletRequest request) {
        ApiError apiError = new ApiError(httpStatus.value(), exception.getMessage(), request.getRequestURL().toString());
        return new ResponseEntity<>(apiError, httpStatus);
    }
}
