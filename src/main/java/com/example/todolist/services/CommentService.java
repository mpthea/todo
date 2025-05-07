package com.example.todolist.services;

import com.example.todolist.dto.CommentDTO;
import com.example.todolist.dto.TaskDTO;
import com.example.todolist.models.Comment;
import com.example.todolist.models.Task;
import com.example.todolist.models.User;
import com.example.todolist.repositories.CommentRepository;
import com.example.todolist.repositories.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private TaskRepository taskRepository;
    private ModelMapper modelMapper;

    public CommentDTO createComment(CommentDTO commentDTO, User author, Long taskId){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));

        Comment comment = modelMapper.map(commentDTO, Comment.class);
        comment.setAuthor(author);
        comment.setTask(task);

        Comment savedComment = commentRepository.save(comment);
        return modelMapper.map(savedComment, CommentDTO.class);
    }

    public List<CommentDTO> getComments(Long taskId){
        Task task = taskRepository.findById(taskId).
                orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));
        List<Comment> comments = commentRepository.findByTask(task);
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentDTO.class))
                .collect(Collectors.toList());
    }

    public CommentDTO getCommentById(Long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));
        return modelMapper.map(comment, CommentDTO.class);
    }

    public void deleteComment(Long commentId){
        if(commentRepository.existsById(commentId)){
            commentRepository.deleteById(commentId);
        }
        else{
            throw new EntityNotFoundException("Comment not found with id: " + commentId);
        }
    }

    public CommentDTO updateComment(Long commentId, CommentDTO commentDTO){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));
        modelMapper.map(commentDTO, comment);

        Comment updateComment = commentRepository.save(comment);
        return modelMapper.map(updateComment, CommentDTO.class);
    }

}
