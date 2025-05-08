package com.example.todolist.dto;

import com.example.todolist.models.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @Schema(description = "Email пользователя")
    @Email(message = "Invalid email")
    private String email;

    @Schema(description = "Имя пользователя")
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @Schema(description = "Пароль пользователя")
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, max = 32, message = "Password must be between 8 and 32 characters")
    private String password;

    @Schema(description = "Роль пользователя")
    @NotNull(message = "User role must be specified")
    private Role role;
}
