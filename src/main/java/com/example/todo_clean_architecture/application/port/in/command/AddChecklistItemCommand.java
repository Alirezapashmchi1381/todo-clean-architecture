package com.example.todo_clean_architecture.application.port.in.command;

import com.example.todo_clean_architecture.domain.model.TaskId;
import java.util.Objects;

public record AddChecklistItemCommand(TaskId taskId, String description) {
    public AddChecklistItemCommand {
        Objects.requireNonNull(taskId, "TaskId must not be null");
        Objects.requireNonNull(description, "Description must not be null");
    }
}
