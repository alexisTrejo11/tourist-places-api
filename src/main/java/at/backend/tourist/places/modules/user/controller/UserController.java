package at.backend.tourist.places.modules.user.controller;

import at.backend.tourist.places.core.shared.Response.ResponseWrapper;
import at.backend.tourist.places.modules.user.controller.annotation.*;
import at.backend.tourist.places.modules.user.dtos.UserDTO;
import at.backend.tourist.places.modules.user.dtos.UserInsertDTO;
import at.backend.tourist.places.modules.user.service.UserService;
import at.backend.tourist.places.core.shared.Enum.Role;
import at.backend.tourist.places.modules.auth.jwt.JwtService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/users/admin")
@Tag(name = "User Management", description = "Admin endpoints for managing users and their accounts")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping("/me")
    @GetCurrentUserOperation
    private ResponseEntity<ResponseWrapper<UserDTO>> me(HttpServletRequest request) {
        String email = jwtService.getEmailFromRequest(request);
        UserDTO user = userService.getByEmail(email);
        return ResponseEntity.ok(ResponseWrapper.found(user, "user"));
    }

    @GetMapping("/all")
    @GetAllUsersOperation
    private ResponseEntity<ResponseWrapper<List<UserDTO>>> getAllUsers() {
        List<UserDTO> users = userService.getAll();
        return ResponseEntity.ok(ResponseWrapper.found(users, "Users"));
    }

    @GetMapping("/{userId}")
    @GetUserByIdOperation
    private ResponseEntity<ResponseWrapper<UserDTO>> getUserById(
            @Parameter(description = "Unique identifier of the user", example = "1", required = true)
            @PathVariable Long userId) {

        UserDTO userDTO = userService.getById(userId);
        return ResponseEntity.ok(ResponseWrapper.found(userDTO, "user"));
    }

    @GetMapping("by-role/{role}")
    @GetUsersByRoleOperation
    private ResponseEntity<ResponseWrapper<Page<UserDTO>>> getUserByRole(
            @Parameter(description = "Role to filter by", example = "ADMIN", required = true)
            @PathVariable Role role,
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "10")
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<UserDTO> userPage = userService.getByRole(role, pageable);
        return ResponseEntity.ok(ResponseWrapper.found(userPage, "user"));
    }

    @PostMapping
    @CreateUserOperation
    private ResponseEntity<ResponseWrapper<UserDTO>> createUser(
            @Parameter(description = "Details of the user to create", required = true)
            @Valid @RequestBody UserInsertDTO insertDTO) {

        UserDTO userDTO = userService.create(insertDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.created(userDTO, "user"));
    }

    @PutMapping("{userId}/update-role/{role}")
    @UpdateUserRoleOperation
    private ResponseEntity<ResponseWrapper<Void>> updateUserRole(
            @Parameter(description = "ID of the user to update", example = "1", required = true)
            @PathVariable Long userId,
            @Parameter(description = "New role to assign", example = "ADMIN", required = true)
            @PathVariable Role role) {

        userService.updateRole(userId, role);
        return ResponseEntity.ok(ResponseWrapper.ok("user", "role update"));
    }
}