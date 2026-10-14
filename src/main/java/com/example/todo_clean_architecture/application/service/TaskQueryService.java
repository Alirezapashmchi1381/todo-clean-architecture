package com.example.todo_clean_architecture.application.service;

import com.example.todo_clean_architecture.application.port.in.TaskQueryUseCase;
import com.example.todo_clean_architecture.application.port.out.TaskRepository;
import com.example.todo_clean_architecture.domain.model.Task;
import com.example.todo_clean_architecture.domain.model.TaskId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TaskQueryService implements TaskQueryUseCase {

    private final TaskRepository taskRepository;

    public TaskQueryService(TaskRepository taskRepository) {
        this.taskRepository = Objects.requireNonNull(taskRepository, "TaskRepository must not be null");
    }

    @Override
    public Optional<Task> findTaskById(TaskId taskId) {
        return taskRepository.findById(taskId);
    }

    @Override
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }
}
