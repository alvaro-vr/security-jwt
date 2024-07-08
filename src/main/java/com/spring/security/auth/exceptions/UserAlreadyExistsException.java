package com.spring.security.auth.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String usernameAlreadyExists) {
        super(usernameAlreadyExists);
    }
}
