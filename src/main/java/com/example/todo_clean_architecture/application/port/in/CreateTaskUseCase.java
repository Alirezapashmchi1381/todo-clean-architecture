package com.example.todo_clean_architecture.application.port.in;

import com.example.todo_clean_architecture.application.port.in.command.CreateTaskCommand;

public interface CreateTaskUseCase {
    void createTask(CreateTaskCommand command);
}
