import com.example.todolist.controllers.TaskController;
import com.example.todolist.dto.TaskDTO;

import com.example.todolist.models.User;
import com.example.todolist.models.enums.Role;
import com.example.todolist.models.enums.TaskPriority;
import com.example.todolist.models.enums.TaskStatus;
import com.example.todolist.services.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.clearContext();
    }

    @Test
    void createTask_shouldReturnCreatedTask() {
        // Arrange
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("Test Task");
        taskDTO.setDescription("Test Description");
        taskDTO.setStatus(TaskStatus.PENDING);
        taskDTO.setPriority(TaskPriority.MEDIUM);
        taskDTO.setAssigneeEmail("assignee@mail.com");

        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("author@mail.com");
        mockUser.setRole(Role.USER);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(mockUser, null, List.of());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        TaskDTO createdTask = new TaskDTO();
        createdTask.setTitle("Test Task");
        createdTask.setDescription("Test Description");
        createdTask.setStatus(TaskStatus.PENDING);
        createdTask.setPriority(TaskPriority.MEDIUM);
        createdTask.setAuthorId(1L);
        createdTask.setAssigneeEmail("assignee@mail.com");

        when(taskService.createTask(any(TaskDTO.class), any(User.class))).thenReturn(createdTask);

        // Act
        ResponseEntity<TaskDTO> response = taskController.createTask(taskDTO);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Test Task", response.getBody().getTitle());
        assertEquals("Test Description", response.getBody().getDescription());
        assertEquals(TaskStatus.PENDING, response.getBody().getStatus());
        assertEquals(TaskPriority.MEDIUM, response.getBody().getPriority());
        assertEquals(1L, response.getBody().getAuthorId());

        verify(taskService, times(1)).createTask(any(TaskDTO.class), any(User.class));
    }

    @Test
    void createTask_shouldReturn401IfNotAuthenticated() {
        // Arrange
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("Test Task");

        // Act
        ResponseEntity<TaskDTO> response = taskController.createTask(taskDTO);

        // Assert
        assertEquals(401, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(taskService, never()).createTask(any(TaskDTO.class), any(User.class));
    }

    @Test
    void deleteTaskById_shouldReturnNoContent(){
        // Arrange
        Long taskId = 123L;

        // Act
        ResponseEntity<HttpStatus> response = taskController.deleteTaskById(taskId);

        //Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(taskService, times(1)).deleteTaskById(taskId);
    }
}
