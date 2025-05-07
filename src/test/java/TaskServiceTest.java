import com.example.todolist.dto.TaskDTO;
import com.example.todolist.models.Task;
import com.example.todolist.models.User;
import com.example.todolist.models.enums.Role;
import com.example.todolist.models.enums.TaskPriority;
import com.example.todolist.models.enums.TaskStatus;
import com.example.todolist.repositories.TaskRepository;
import com.example.todolist.repositories.UserRepository;
import com.example.todolist.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TaskService taskService;

    private User author;
    private Task task;
    private TaskDTO taskDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        author = new User();
        author.setId(1L);
        author.setEmail("author@mail.com");
        author.setRole(Role.USER);

        task = new Task();
        task.setId(10L);
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setStatus(TaskStatus.PENDING);
        task.setPriority(TaskPriority.MEDIUM);
        task.setAuthor(author);

        taskDTO = new TaskDTO();
        taskDTO.setTitle("Test Task");
        taskDTO.setDescription("Test Description");
        taskDTO.setStatus(TaskStatus.PENDING);
        taskDTO.setPriority(TaskPriority.MEDIUM);
        taskDTO.setAuthorId(1L);
        taskDTO.setAuthorEmail("author@mail.com");
    }
    @Test
    void createTask_shouldReturnCreatedTaskDTO() {
        // Arrange
        User assignee = new User();
        assignee.setId(2L);
        assignee.setEmail("assignee@mail.com");
        assignee.setRole(Role.USER);

        // Важно! Устанавливаем assigneeEmail в taskDTO
        taskDTO.setAssigneeEmail("assignee@mail.com");

        when(modelMapper.map(taskDTO, Task.class)).thenReturn(task);
        when(userRepository.findByEmail("author@mail.com")).thenReturn(Optional.of(author));
        when(userRepository.findByEmail("assignee@mail.com")).thenReturn(Optional.of(assignee));
        when(taskRepository.save(any(Task.class))).thenReturn(task);
        when(modelMapper.map(task, TaskDTO.class)).thenReturn(taskDTO);

        // Act
        TaskDTO result = taskService.createTask(taskDTO, author);

        // Assert
        assertNotNull(result);
        assertEquals(taskDTO.getTitle(), result.getTitle());
        verify(taskRepository, times(1)).save(any(Task.class));
        verify(userRepository, times(1)).findByEmail("assignee@mail.com");
    }



    @Test
    void getTaskById_shouldReturnTaskDTO() {
        // Arrange
        when(taskRepository.findById(10L)).thenReturn(Optional.of(task));
        when(modelMapper.map(task, TaskDTO.class)).thenReturn(taskDTO);

        // Act
        TaskDTO result = taskService.getTaskById(10L);

        // Assert
        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
        verify(taskRepository, times(1)).findById(10L);
    }

    @Test
    void deleteTaskById_shouldInvokeRepository() {
        // Arrange
        when(taskRepository.existsById(10L)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(10L);

        // Act
        taskService.deleteTaskById(10L);

        // Assert
        verify(taskRepository, times(1)).deleteById(10L);
        verify(taskRepository, times(1)).existsById(10L);
    }

}