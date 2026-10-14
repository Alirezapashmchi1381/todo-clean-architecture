package com.example.todo_clean_architecture.application.port.out;

import com.example.todo_clean_architecture.domain.model.Task;
import com.example.todo_clean_architecture.domain.model.TaskId;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Task save(Task task);
    Optional<Task> findById(TaskId id);
    List<Task> findAll();
}
