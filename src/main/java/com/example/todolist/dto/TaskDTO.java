package com.example.todolist.dto;

import com.example.todolist.models.enums.TaskPriority;
import com.example.todolist.models.enums.TaskStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    @NotBlank(message = "Task title cannot be empty")
    @Size(max = 50, message = "The task title must not exceed 50 characters")
    private String title;

    @NotBlank(message = "Task description cannot be empty")
    private String description;

    @NotNull(message = "Task status cannot be empty")
    private TaskStatus status;

    @NotNull(message = "Task priority cannot be empty")
    private TaskPriority priority;

    @Email(message = "Incorrect author email")
    private String authorEmail;

    @Email(message = "Incorrect assignee email")
    private String assigneeEmail;


    private Long authorId;
    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public long getAuthorId() {
        return authorId;
    }
}
