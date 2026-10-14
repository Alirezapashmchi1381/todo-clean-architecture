package com.example.todo_clean_architecture.application.port.in;

import com.example.todo_clean_architecture.domain.model.Task;
import com.example.todo_clean_architecture.domain.model.TaskId;
import java.util.List;
import java.util.Optional;

public interface TaskQueryUseCase {
    Optional<Task> findTaskById(TaskId taskId);
    List<Task> findAllTasks();
}
