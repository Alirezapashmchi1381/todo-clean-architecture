package com.example.todo_clean_architecture.domain.model;

import com.example.todo_clean_architecture.domain.exception.DomainValidationException;
import java.util.Objects;
import java.util.UUID;


public class ChecklistItem {
    private final UUID id;
    private String description;
    private boolean completed;

    public ChecklistItem(UUID id, String description) {
        this.id = Objects.requireNonNull(id, "Checklist item id cannot be null");
        setDescription(description);
        this.completed = false;
    }

    // Reconstruction constructor (e.g., when mapped back from persistence)
    public ChecklistItem(UUID id, String description, boolean completed) {
        this.id = Objects.requireNonNull(id, "Checklist item id cannot be null");
        setDescription(description);
        this.completed = completed;
    }

    public void complete() {
        this.completed = true;
    }

    private void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new DomainValidationException("Checklist item description cannot be empty");
        }
        this.description = description.trim();
    }

    public UUID getId() { return id; }
    public String getDescription() { return description; }
    public boolean isCompleted() { return completed; }
}