package com.example.todo_clean_architecture.domain.model;

import java.util.Objects;
import java.util.UUID;


public record TaskId(UUID value) {
    public TaskId{
        Objects.requireNonNull(value, "TaskID can not be null");
    }

    public static TaskId generate(){
        return new TaskId(UUID.randomUUID());
    }

    public static TaskId of(UUID value){
        return new TaskId(value);
    }
}
