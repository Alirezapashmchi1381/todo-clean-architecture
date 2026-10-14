package com.example.todo_clean_architecture.application.service;

import com.example.todo_clean_architecture.application.port.in.AddChecklistItemUseCase;
import com.example.todo_clean_architecture.application.port.in.CompleteChecklistItemUseCase;
import com.example.todo_clean_architecture.application.port.in.CompleteTaskUseCase;
import com.example.todo_clean_architecture.application.port.in.CreateTaskUseCase;
import com.example.todo_clean_architecture.application.port.in.command.AddChecklistItemCommand;
import com.example.todo_clean_architecture.application.port.in.command.CompleteChecklistItemCommand;
import com.example.todo_clean_architecture.application.port.in.command.CompleteTaskCommand;
import com.example.todo_clean_architecture.application.port.in.command.CreateTaskCommand;
import com.example.todo_clean_architecture.application.port.out.EventPublisherPort;
import com.example.todo_clean_architecture.application.port.out.TaskRepository;
import com.example.todo_clean_architecture.domain.event.TaskCompletedEvent;
import com.example.todo_clean_architecture.domain.exception.DomainValidationException;
import com.example.todo_clean_architecture.domain.model.Task;
import com.example.todo_clean_architecture.domain.model.TaskId;
import java.util.Objects;

public class TaskCommandService implements CreateTaskUseCase, AddChecklistItemUseCase,
        CompleteTaskUseCase, CompleteChecklistItemUseCase {

    private final TaskRepository taskRepository;
    private final EventPublisherPort eventPublisher;

    public TaskCommandService(TaskRepository taskRepository, EventPublisherPort eventPublisher) {
        this.taskRepository = Objects.requireNonNull(taskRepository, "TaskRepository must not be null");
        this.eventPublisher = Objects.requireNonNull(eventPublisher, "EventPublisher must not be null");
    }

    @Override
    public void createTask(CreateTaskCommand command) {
        Task task = Task.create(TaskId.generate(), command.title(), command.priority());
        taskRepository.save(task);
    }

    @Override
    public void addChecklistItem(AddChecklistItemCommand command) {
        Task task = taskRepository.findById(command.taskId())
                .orElseThrow(() -> new DomainValidationException("Task not found: " + command.taskId()));
        task.addChecklistItem(command.description());
        taskRepository.save(task);
    }

    @Override
    public void completeChecklistItem(CompleteChecklistItemCommand command) {
        Task task = taskRepository.findById(command.taskId())
                .orElseThrow(() -> new DomainValidationException("Task not found: " + command.taskId()));
        task.completeChecklistItem(command.itemId());
        taskRepository.save(task);
    }

    @Override
    public void completeTask(CompleteTaskCommand command) {
        Task task = taskRepository.findById(command.taskId())
                .orElseThrow(() -> new DomainValidationException("Task not found: " + command.taskId()));
        task.complete();
        taskRepository.save(task);
        for (Object event : task.pullDomainEvents()) {
            if (event instanceof TaskCompletedEvent taskCompletedEvent) {
                eventPublisher.publish(taskCompletedEvent);
            }
        }
    }
}
