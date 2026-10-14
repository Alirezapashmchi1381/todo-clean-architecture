package com.example.todo_clean_architecture.domain;

import com.example.todo_clean_architecture.domain.exception.DomainValidationException;
import com.example.todo_clean_architecture.domain.model.Priority;
import com.example.todo_clean_architecture.domain.model.Task;
import com.example.todo_clean_architecture.domain.model.TaskId;
import com.example.todo_clean_architecture.domain.model.TaskStatus;
import com.example.todo_clean_architecture.domain.model.TaskTitle;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void shouldNotCompleteTaskWhenChecklistHasPendingItems() {
        Task task = Task.create(TaskId.generate(), new TaskTitle("Deploy service"), Priority.HIGH);
        task.addChecklistItem("Write migrations");

        assertThrows(DomainValidationException.class, task::complete);
        assertEquals(TaskStatus.TODO, task.getStatus());
    }

    @Test
    void shouldCompleteTaskWhenAllItemsAreDone() {
        Task task = Task.create(TaskId.generate(), new TaskTitle("Deploy service"), Priority.HIGH);
        task.addChecklistItem("Write migrations");

        var itemId = task.getChecklist().get(0).getId();
        task.completeChecklistItem(itemId);

        task.complete();

        assertEquals(TaskStatus.COMPLETED, task.getStatus());
        assertNotNull(task.getCompletedAt());
        assertEquals(1, task.pullDomainEvents().size());
    }
}