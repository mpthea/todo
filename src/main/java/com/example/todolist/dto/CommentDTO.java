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

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    @NotBlank(message = "Comment text cannot be empty")
    private String text;
    @Email(message = "Incorrect author email")
    private String authorEmail;
    @NotBlank(message = "Task name cannot be empty")
    private String taskTitle;
}
