package com.example.todo_clean_architecture.application.port.out;

import com.example.todo_clean_architecture.domain.event.TaskCompletedEvent;

public interface EventPublisherPort {
    void publish(TaskCompletedEvent event);
}
