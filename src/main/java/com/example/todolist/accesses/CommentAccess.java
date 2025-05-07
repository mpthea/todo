package com.example.todolist.accesses;

import com.example.todolist.models.Comment;
import com.example.todolist.models.User;
import com.example.todolist.repositories.CommentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CommentAccess {

    @Autowired
    private CommentRepository commentRepository;

    public boolean hasAccess(Long commentId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        return user.getId().equals(comment.getAuthor().getId());
    }
}
