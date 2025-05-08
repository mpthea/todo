package com.example.todolist.controllers;

import com.example.todolist.dto.TaskDTO;
import com.example.todolist.models.User;
import com.example.todolist.services.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;

import io.swagger.v3.oas.annotations.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Tag(name = "Tasks API", description = "API для управления задачами")
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Operation(summary = "Создать задачу", description = "Создаёт новую задачу")
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody @Valid TaskDTO taskDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = (User) authentication.getPrincipal();
        taskDTO.setAuthorId(user.getId());

        TaskDTO createdTask = taskService.createTask(taskDTO, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }



    @Operation(summary = "Удалить задачу по ID", description = "Удаляет задачу по ID")
    @Parameter(name = "id", description = "ID задачи")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTaskById(@PathVariable Long taskId){
        taskService.deleteTaskById(taskId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Получить все задачи", description = "Возвращает список всех задач")
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getTasks(){
        return ResponseEntity.ok(taskService.getTasks());
    }

    @Operation(summary = "Получить задачу по ID", description = "Возвращает задачу по ID")
    @Parameter(name = "id", description = "ID задачи")
    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long taskId){
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }


    @Operation(summary = "Обновить задачу по ID", description = "Обновляет задачу по ID")
    @Parameter(name = "id", description = "ID задачи")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @taskAccess.hasAccess(#taskId)")
    public ResponseEntity<TaskDTO> updateTaskById(@PathVariable Long taskId, @RequestBody @Valid TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.updateTask(taskId, taskDTO));
    }

    @Operation(summary = "Получить задачи с фильтрацией и пагинацией", description = "Возвращает страницу задач с возможностью фильтрации")
    @Parameter(name = "page", description = "Номер страницы")
    @Parameter(name = "size", description = "Количество задач на странице")
    @Parameter(name = "status", description = "Статус задачи")
    @Parameter(name = "priority", description = "Приоритет задачи")
    @Parameter(name = "authorId", description = "ID автора задачи")
    @Parameter(name = "assigneeId", description = "ID исполнителя задачи")
    @GetMapping
    public ResponseEntity<Page<TaskDTO>> getTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) Long assigneeId
    ) {
        return ResponseEntity.ok(taskService.getTasks(page, size, status, priority, authorId, assigneeId));
    }


}
