package com.example.todolist.dto;

import com.example.todolist.models.Task;
import com.example.todolist.models.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    @Schema(description = "Текст комментария")
    @NotBlank(message = "Comment text cannot be empty")
    private String text;

    @Schema(description = "Email автора комментария")
    @Email(message = "Incorrect author email")
    private String authorEmail;

    @Schema(description = "Название связанной задачи")
    @NotBlank(message = "Task name cannot be empty")
    private String taskTitle;
}
