package com.example.todolist.models;

import com.example.todolist.models.enums.TaskPriority;
import com.example.todolist.models.enums.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Task title cannot be empty")
    @Size(max = 50, message = "Task title must not exceed 50 characters")
    private String title;

    @NotBlank(message = "Task description cannot be empty")
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Task status must be specified")
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Task priority must be specified")
    private TaskPriority priority;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @ManyToOne
    @JoinColumn(name = "assignee_id", nullable = false)
    private User assignee;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
}