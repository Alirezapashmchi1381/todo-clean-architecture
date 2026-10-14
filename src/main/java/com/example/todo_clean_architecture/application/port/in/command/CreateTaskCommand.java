package com.example.todo_clean_architecture.application.port.in.command;

import com.example.todo_clean_architecture.domain.model.Priority;
import com.example.todo_clean_architecture.domain.model.TaskTitle;
import java.util.Objects;

public record CreateTaskCommand(TaskTitle title, Priority priority) {
    public CreateTaskCommand {
        Objects.requireNonNull(title, "Title must not be null");
        Objects.requireNonNull(priority, "Priority must not be null");
    }
}
