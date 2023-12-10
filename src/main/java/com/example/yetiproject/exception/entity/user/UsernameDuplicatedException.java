package com.example.yetiproject.exception.entity.user;

public class UsernameDuplicatedException extends RuntimeException{
    public UsernameDuplicatedException(String message) {
        super(message);
    }
}
