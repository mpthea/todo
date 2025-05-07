package com.example.todolist.services;

import com.example.todolist.dto.TaskDTO;
import com.example.todolist.models.Task;
import com.example.todolist.models.User;
import com.example.todolist.models.enums.TaskStatus;
import com.example.todolist.repositories.TaskRepository;
import com.example.todolist.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    public TaskDTO createTask(TaskDTO taskDTO, User author){
        Task task = modelMapper.map(taskDTO, Task.class);
        task.setAuthor(author);

        User assignee = userRepository.findByEmail(taskDTO.getAssigneeEmail())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        task.setAssignee(assignee);


        Task savedTask = taskRepository.save(task);
        return modelMapper.map(savedTask, TaskDTO.class);
    }

    public List<TaskDTO> getTasks(){
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(task -> modelMapper.map(task, TaskDTO.class))
                .collect(Collectors.toList());

    }

    public TaskDTO getTaskById(Long taskId){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));
        return modelMapper.map(task, TaskDTO.class);
    }

    public void deleteTaskById(Long taskId){
        if(taskRepository.existsById(taskId)){
            taskRepository.deleteById(taskId);
            log.info("Delete task with id: {}", taskId);
        }
        else{
            log.warn("Not found id: {}", taskId);
        }
    }

    public TaskDTO updateTask(Long taskId, TaskDTO taskDTO){
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));

        modelMapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setPropertyCondition(Conditions.isNotNull());

        modelMapper.map(taskDTO, task);

        if (taskDTO.getAssigneeEmail() != null) {
            User assignee = userRepository.findByEmail(taskDTO.getAssigneeEmail())
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));

            task.setAssignee(assignee);
        }

        Task updatedTask = taskRepository.save(task);
        return modelMapper.map(updatedTask, TaskDTO.class);
    }

    public Page<TaskDTO> getTasks(
            int page,
            int size,
            String status,
            String priority,
            Long authorId,
            Long assigneeId
    ) {
        return taskRepository.findAll(
                Pageable.ofSize(size).withPage(page),
                status,
                priority,
                authorId,
                assigneeId
        ).map(task -> modelMapper.map(task, TaskDTO.class));
    }

    public void updateTaskStatus(Long taskId, TaskStatus status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found"));

        if (status == null || !Arrays.stream(TaskStatus.values())
                .anyMatch(s -> s.equals(status))) {
            throw new IllegalArgumentException("Incorrect status");
        }

        task.setStatus(status);
        taskRepository.save(task);
    }



}
