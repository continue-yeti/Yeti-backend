package com.example.yetiproject.exception.entity.stadium;

public class StadiumNotFoundException extends RuntimeException{
    public StadiumNotFoundException(String message) {
        super(message);
    }
}
