package com.example.todolist.controllers;

import com.example.todolist.dto.UserDTO;
import com.example.todolist.services.UserService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Tag(name = "User API", description = "API для управления профилем пользователя")
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Получить профиль пользователя", description = "Возвращает профиль текущего пользователя")
    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getUserProfile(Principal principal) {
        String email = principal.getName();
        UserDTO userDTO = userService.getUserByEmail(email);
        return ResponseEntity.ok(userDTO);
    }

}
