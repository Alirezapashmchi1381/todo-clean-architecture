package com.example.todo_clean_architecture.domain.event;

import com.example.todo_clean_architecture.domain.model.TaskId;
import java.time.Instant;



public record TaskCompletedEvent(TaskId taskIdk, Instant occuredOn) {
    public static TaskCompletedEvent of(TaskId taskId){
        return new TaskCompletedEvent(taskId, Instant.now());
    }
    
}
