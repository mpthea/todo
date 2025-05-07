import com.example.todolist.controllers.UserController;
import com.example.todolist.dto.UserDTO;
import com.example.todolist.models.enums.Role;
import com.example.todolist.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserProfile_shouldReturnUserDTO() {
        //Arrange
        String email = "user@mail.ru";
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(email);

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);
        userDTO.setRole(Role.USER);

        when(userService.getUserByEmail(email)).thenReturn(userDTO);

        //Act
        ResponseEntity<UserDTO> response = userController.getUserProfile(principal);

        //Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(email, response.getBody().getEmail());
        assertEquals(Role.USER, response.getBody().getRole());

        verify(userService, times(1)).getUserByEmail(email);

    }
}
