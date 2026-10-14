package com.example.todo_clean_architecture.application.port.in.command;

import com.example.todo_clean_architecture.domain.model.TaskId;
import java.util.Objects;

public record CompleteTaskCommand(TaskId taskId) {
    public CompleteTaskCommand {
        Objects.requireNonNull(taskId, "TaskId must not be null");
    }
}
