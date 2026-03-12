package com.riddhi.payflow.controller;

import com.riddhi.payflow.dto.ApiResponse;
import com.riddhi.payflow.dto.LoginRequestDTO;
import com.riddhi.payflow.dto.LoginResponseDTO;
import com.riddhi.payflow.dto.UserRequestDTO;
import com.riddhi.payflow.entity.Transaction;
import com.riddhi.payflow.entity.User;
import com.riddhi.payflow.service.TransactionService;
import com.riddhi.payflow.service.UserService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final TransactionService transactionService;

    public UserController(UserService userService,
                          TransactionService transactionService) {
        this.userService = userService;
        this.transactionService = transactionService;
    }

    @PostMapping
    public ApiResponse<User> createUser(
            @Valid @RequestBody UserRequestDTO dto) {

        User user = userService.createUser(dto);

        return new ApiResponse<>(
                true,
                "User created successfully",
                user
        );
    }

    @GetMapping
    public ApiResponse<List<User>> getAllUsers() {

        return new ApiResponse<>(
                true,
                "Users fetched successfully",
                userService.getAllUsers()
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<User> getUserById(@PathVariable Long id) {

        User user = userService.getUserById(id);

        return new ApiResponse<>(
                true,
                "User fetched successfully",
                user
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        return new ApiResponse<>(
                true,
                "User deleted successfully",
                null
        );
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO dto) {

        return new ApiResponse<>(
                true,
                "Login successful",
                userService.login(dto)
        );
    }
}