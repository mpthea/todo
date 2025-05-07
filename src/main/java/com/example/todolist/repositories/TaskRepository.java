package com.example.todolist.repositories;

import com.example.todolist.models.Task;
import com.example.todolist.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAuthor(User author);
    List<Task> findByAssignee(User assignee);

    Page<Task> findAll(
            Pageable pageable,
            String status,
            String priority,
            Long authorId,
            Long assigneeId
    );
}
