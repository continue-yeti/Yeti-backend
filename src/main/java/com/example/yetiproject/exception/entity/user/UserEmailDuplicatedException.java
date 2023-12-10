package com.example.yetiproject.exception.entity.user;

public class UserEmailDuplicatedException extends RuntimeException{
    public UserEmailDuplicatedException(String message) {
        super(message);
    }
}
