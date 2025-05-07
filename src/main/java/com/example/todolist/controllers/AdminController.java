package com.example.todolist.controllers;

import com.example.todolist.dto.UserDTO;
import com.example.todolist.models.enums.Role;
import com.example.todolist.models.enums.TaskStatus;
import com.example.todolist.services.TaskService;
import com.example.todolist.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;
    private final TaskService taskService;

    @Autowired
    public AdminController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/users/{id}/role")
    public ResponseEntity<Void> updateUserRole(@PathVariable Long id, @RequestParam Role role) {
        userService.updateUserRole(id, role);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{taskId}/status")
    @PreAuthorize("hasRole('ADMIN') or @taskAccess.hasAccess(#taskId)")
    public ResponseEntity<Void> updateTaskStatus(@PathVariable Long taskId, @RequestParam TaskStatus status) {
        taskService.updateTaskStatus(taskId, status);
        return ResponseEntity.noContent().build();
    }


}
