package com.example.todo_clean_architecture.application.port.in;

import com.example.todo_clean_architecture.application.port.in.command.CompleteTaskCommand;

public interface CompleteTaskUseCase {
    void completeTask(CompleteTaskCommand command);
}
