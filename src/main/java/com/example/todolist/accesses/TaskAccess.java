package com.example.todolist.accesses;

import com.example.todolist.models.Task;
import com.example.todolist.models.User;
import com.example.todolist.repositories.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class TaskAccess {

    @Autowired
    private TaskRepository taskRepository;

    public boolean hasAccess(Long taskId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        return user.getId().equals(task.getAuthor().getId())
                || user.getId().equals(task.getAssignee().getId());
    }
}

