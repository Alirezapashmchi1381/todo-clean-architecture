package com.example.todo_clean_architecture.domain.model;

import com.example.todo_clean_architecture.domain.event.TaskCompletedEvent;
import com.example.todo_clean_architecture.domain.exception.DomainValidationException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Task {
    private final TaskId id;
    private TaskTitle title;
    private TaskStatus status;
    private Priority priority;
    private final List<ChecklistItem> checklist;
    private Instant completedAt;

    // Transient list of domain events to publish upon persistence
    private final List<Object> domainEvents = new ArrayList<>();

    // Creation Factory Method
    public static Task create(TaskId id, TaskTitle title, Priority priority) {
        return new Task(id, title, TaskStatus.TODO, priority, new ArrayList<>(), null);
    }

    // Package-private or public constructor for reconstituting state from database
    public Task(
        TaskId id, 
        TaskTitle title, 
        TaskStatus status, 
        Priority priority, 
        List<ChecklistItem> checklist, 
        Instant completedAt
    ) {
        this.id = Objects.requireNonNull(id, "Id cannot be null");
        this.title = Objects.requireNonNull(title, "Title cannot be null");
        this.status = Objects.requireNonNull(status, "Status cannot be null");
        this.priority = Objects.requireNonNull(priority, "Priority cannot be null");
        this.checklist = new ArrayList<>(Objects.requireNonNull(checklist, "Checklist cannot be null"));
        this.completedAt = completedAt;
    }

    // --- Business Methods / Invariants ---

    public void addChecklistItem(String description) {
        if (this.status == TaskStatus.COMPLETED) {
            throw new DomainValidationException("Cannot add checklist items to an already completed task");
        }
        this.checklist.add(new ChecklistItem(UUID.randomUUID(), description));
    }

    public void completeChecklistItem(UUID itemId) {
        ChecklistItem item = this.checklist.stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new DomainValidationException("Checklist item not found: " + itemId));
        
        item.complete();
    }

    public void complete() {
        if (this.status == TaskStatus.COMPLETED) {
            throw new DomainValidationException("Task is already completed");
        }

        boolean hasUnfinishedItems = this.checklist.stream().anyMatch(item -> !item.isCompleted());
        if (hasUnfinishedItems) {
            throw new DomainValidationException("Cannot complete task with unfinished checklist items");
        }

        this.status = TaskStatus.COMPLETED;
        this.completedAt = Instant.now();

        // Register domain event
        this.domainEvents.add(TaskCompletedEvent.of(this.id));
    }

    public void startProgress() {
        if (this.status == TaskStatus.COMPLETED) {
            throw new DomainValidationException("Cannot transition a completed task to IN_PROGRESS");
        }
        this.status = TaskStatus.IN_PROGRESS;
    }

    // --- Domain Event Management ---

    public List<Object> pullDomainEvents() {
        List<Object> events = List.copyOf(this.domainEvents);
        this.domainEvents.clear();
        return events;
    }

    // --- Getters returning immutable views ---

    public TaskId getId() { return id; }
    public TaskTitle getTitle() { return title; }
    public TaskStatus getStatus() { return status; }
    public Priority getPriority() { return priority; }
    public List<ChecklistItem> getChecklist() { return Collections.unmodifiableList(checklist); }
    public Instant getCompletedAt() { return completedAt; }
}