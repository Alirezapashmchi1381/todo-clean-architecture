package com.example.todo_clean_architecture.application.port.in;

import com.example.todo_clean_architecture.application.port.in.command.AddChecklistItemCommand;

public interface AddChecklistItemUseCase {
    void addChecklistItem(AddChecklistItemCommand command);
}
