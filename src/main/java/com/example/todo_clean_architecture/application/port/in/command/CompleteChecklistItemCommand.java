package com.example.todo_clean_architecture.application.port.in.command;

import com.example.todo_clean_architecture.domain.model.TaskId;
import java.util.Objects;
import java.util.UUID;

public record CompleteChecklistItemCommand(TaskId taskId, UUID itemId) {
    public CompleteChecklistItemCommand {
        Objects.requireNonNull(taskId, "TaskId must not be null");
        Objects.requireNonNull(itemId, "ItemId must not be null");
    }
}
