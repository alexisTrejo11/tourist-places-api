package at.backend.tourist.places.modules.user.controller;

import at.backend.tourist.places.core.swagger.ApiResponseExamples;
import at.backend.tourist.places.core.shared.Response.ResponseWrapper;
import at.backend.tourist.places.modules.user.dtos.UserDTO;
import at.backend.tourist.places.modules.user.dtos.UserInsertDTO;
import at.backend.tourist.places.modules.user.service.UserService;
import at.backend.tourist.places.core.shared.Enum.Role;
import at.backend.tourist.places.modules.auth.jwt.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "user Management", description = "Operations related to user management")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    @Operation(summary = "Get current logged-in user", description = "Fetches the details of the currently logged-in user based on the jwt token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "user details retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class), examples = @ExampleObject(value = ApiResponseExamples.USER))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = ApiResponseExamples.UNAUTHORIZED_ACCESS)))
    })
    @GetMapping("/me")
    private ResponseEntity<ResponseWrapper<UserDTO>> me(HttpServletRequest request) {
        String email = jwtService.getEmailFromRequest(request);
        UserDTO user = userService.getByEmail(email);
        return ResponseEntity.ok(ResponseWrapper.found(user, "user"));
    }

    @Operation(summary = "Get all users", description = "Fetches a list of all registered users")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of users retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class),
                            examples = @ExampleObject(value = ApiResponseExamples.USERS))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = ApiResponseExamples.UNAUTHORIZED_ACCESS))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = ApiResponseExamples.FORBIDDEN)))
    })
    @GetMapping("/all")
    private ResponseEntity<ResponseWrapper<List<UserDTO>>> getAllUsers() {
        List<UserDTO> users = userService.getAll();
        return ResponseEntity.ok(ResponseWrapper.found(users, "Users"));
    }

    @Operation(summary = "Get user by ID", description = "Retrieves user details by their unique identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "user found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class),
                            examples = @ExampleObject(value = ApiResponseExamples.USER))),
            @ApiResponse(responseCode = "404", description = "user not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = ApiResponseExamples.NOT_FOUND))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = ApiResponseExamples.UNAUTHORIZED_ACCESS)))
    })
    @GetMapping("/{userId}")
    private ResponseEntity<ResponseWrapper<UserDTO>> getUserById(
            @Parameter(description = "ID of the user to fetch", example = "1", required = true)
            @PathVariable Long userId) {

        UserDTO userDTO = userService.getById(userId);
        return ResponseEntity.ok(ResponseWrapper.found(userDTO, "user"));
    }

    @Operation(summary = "Get users by role", description = "Fetches paginated list of users filtered by role")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users retrieved",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class),
                            examples = @ExampleObject(value = ApiResponseExamples.USERS))),
            @ApiResponse(responseCode = "400", description = "Invalid role",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = ApiResponseExamples.BAD_REQUEST))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = ApiResponseExamples.UNAUTHORIZED_ACCESS)))
    })
    @GetMapping("by-role/{role}")
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

    @Operation(summary = "Create a new user", description = "Registers a new user in the system")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "user created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseWrapper.class),
                            examples = @ExampleObject(value = ApiResponseExamples.USER_CREATED))),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = ApiResponseExamples.BAD_REQUEST))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = ApiResponseExamples.UNAUTHORIZED_ACCESS)))
    })
    @PostMapping
    private ResponseEntity<ResponseWrapper<UserDTO>> createUser(
            @Valid @RequestBody UserInsertDTO insertDTO) {

        UserDTO userDTO = userService.create(insertDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseWrapper.created(userDTO, "user"));
    }

    @Operation(summary = "Update user role", description = "Updates the role of an existing user (Admin only)")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Role updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = ApiResponseExamples.SUCCESS))),
            @ApiResponse(responseCode = "404", description = "user not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = ApiResponseExamples.NOT_FOUND))),
            @ApiResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = ApiResponseExamples.FORBIDDEN)))
    })
    @PutMapping("{userId}/update-role/{role}")
    private ResponseEntity<ResponseWrapper<Void>> updateUserRole(
            @Parameter(description = "ID of the user to update", example = "1", required = true)
            @PathVariable Long userId,
            @Parameter(description = "New role to assign", example = "ADMIN", required = true)
            @PathVariable Role role) {

        userService.updateRole(userId, role);
        return ResponseEntity.ok(ResponseWrapper.ok("user", "role update"));
    }
}