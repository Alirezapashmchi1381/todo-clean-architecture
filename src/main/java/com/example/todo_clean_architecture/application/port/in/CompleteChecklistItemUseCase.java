package com.example.todo_clean_architecture.application.port.in;

import com.example.todo_clean_architecture.application.port.in.command.CompleteChecklistItemCommand;

public interface CompleteChecklistItemUseCase {
    void completeChecklistItem(CompleteChecklistItemCommand command);
}
