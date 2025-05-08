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
import io.swagger.v3.oas.annotations.media.Schema;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    @Schema(description = "Заголовок задачи")
    @NotBlank(message = "Task title cannot be empty")
    @Size(max = 50, message = "The task title must not exceed 50 characters")
    private String title;

    @Schema(description = "Описание задачи")
    @NotBlank(message = "Task description cannot be empty")
    private String description;

    @Schema(description = "Статус задачи")
    @NotNull(message = "Task status cannot be empty")
    private TaskStatus status;

    @Schema(description = "Приоритет задачи")
    @NotNull(message = "Task priority cannot be empty")
    private TaskPriority priority;

    @Schema(description = "Email автора задачи")
    @Email(message = "Incorrect author email")
    private String authorEmail;

    @Schema(description = "Email исполнителя задачи")
    @Email(message = "Incorrect assignee email")
    private String assigneeEmail;

    @Schema(description = "ID автора задачи")
    private Long authorId;

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public long getAuthorId() {
        return authorId;
    }
}
