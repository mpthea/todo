package com.example.todolist.controllers;

import com.example.todolist.dto.CommentDTO;
import com.example.todolist.models.Comment;
import com.example.todolist.models.User;
import com.example.todolist.services.CommentService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comments API", description = "API для управления комментариями к задачам")
@RestController
@RequestMapping("/api/tasks/{taskId}/comments")
@RequiredArgsConstructor
public class CommentController {
    @Autowired
    private CommentService commentService;


    @Operation(summary = "Создать комментарий", description = "Создаёт новый комментарий к задаче")
    @Parameter(name = "taskId", description = "ID задачи")
    @PostMapping
    public ResponseEntity<CommentDTO> createComment (@RequestBody @Valid CommentDTO commentDTO, @PathVariable Long taskId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;

        if (authentication != null && authentication.getPrincipal() != null) {
            user = (User) authentication.getPrincipal();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        CommentDTO createdComment = commentService.createComment(commentDTO, user, taskId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @Operation(summary = "Получить все комментарии к задаче", description = "Возвращает все комментарии к задаче по ID")
    @Parameter(name = "taskId", description = "ID задачи")
    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAllComments(@PathVariable Long taskId) {
        List<CommentDTO> comments = commentService.getComments(taskId);
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "Получить комментарий по ID", description = "Возвращает комментарий по ID")
    @Parameter(name = "id", description = "ID комментария")
    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
        CommentDTO commentDTO = commentService.getCommentById(id);
        if (commentDTO != null) {
            return ResponseEntity.ok(commentDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Удалить комментарий по ID", description = "Удаляет комментарий по ID")
    @Parameter(name = "id", description = "ID комментария")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Обновить комментарий по ID", description = "Обновляет комментарий по ID")
    @Parameter(name = "id", description = "ID комментария")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @commentAccess.hasAccess(#id)")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long id, @RequestBody @Valid CommentDTO commentDTO){
        return ResponseEntity.ok(commentService.updateComment(id, commentDTO));
    }

}
