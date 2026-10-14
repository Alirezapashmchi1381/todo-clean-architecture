package com.example.todo_clean_architecture.domain.model;

import com.example.todo_clean_architecture.domain.exception.DomainValidationException;

public record TaskTitle(String value) {
    private static final int MAX_LENGTH = 100;

    public TaskTitle{
        if (value == null || value.trim().isEmpty()){
            throw new DomainValidationException("enter a value");
        }
        value = value.trim();
        if (value.length() > MAX_LENGTH){
            throw new DomainValidationException("yout length is more then "+ MAX_LENGTH);
        }
    }
    
}
