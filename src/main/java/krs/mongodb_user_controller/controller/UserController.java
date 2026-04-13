package krs.mongodb_user_controller.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import krs.mongodb_user_controller.dto.UserDTO;
import krs.mongodb_user_controller.dto.UserPatchDTO;
import krs.mongodb_user_controller.entity.UserEntity;
import krs.mongodb_user_controller.services.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    // Manually injecting dependencies.
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 1. Create user
    @Operation(summary = "User creation (ADMIN only)", description = "Allows the ADMIN to create a new user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully."),
            @ApiResponse(responseCode = "401", description = "You are not authenticated."),
            @ApiResponse(responseCode = "403", description = "Request access denied."),
            @ApiResponse(responseCode = "409", description = "Data provided caused conflict with an existent user."),
            @ApiResponse(responseCode = "500", description = "Unexpected Server error occurred.")
    })
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserEntity> createUser(@RequestBody @Valid UserDTO userDTO) {
        try {
            UserEntity createdUser = userService.createUser(userDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 2. Read all users
    @Operation(summary = "Users list reading (ADMIN only)", description = "Allows the ADMIN to read all users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users returned successfully."),
            @ApiResponse(responseCode = "401", description = "You are not authenticated."),
            @ApiResponse(responseCode = "403", description = "Request access denied."),
            @ApiResponse(responseCode = "500", description = "Unexpected Server error occurred.")
    })
    @GetMapping("/read/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserEntity>> readAllUsers() {
        try {
            List<UserEntity> usersList = userService.readAllUsers();
            return ResponseEntity.ok(usersList);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 2.1 Read single user
    @Operation(summary = "Single user reading (ADMIN only)", description = "Allows the ADMIN to read a single user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User returned successfully."),
            @ApiResponse(responseCode = "400", description = "User not found."),
            @ApiResponse(responseCode = "401", description = "You are not authenticated."),
            @ApiResponse(responseCode = "403", description = "Request access denied."),
            @ApiResponse(responseCode = "500", description = "Unexpected Server error occurred.")
    })
    @GetMapping("/id/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserEntity> readUser(@PathVariable String userId) {
        try {
            UserEntity selectedUser = userService.readUser(userId);
            return ResponseEntity.ok(selectedUser);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 2.2 Read my user
    @Operation(summary = "Self user reading", description = "Allows any user to read its own data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User returned successfully."),
            @ApiResponse(responseCode = "401", description = "You are not authenticated."),
            @ApiResponse(responseCode = "500", description = "Unexpected Server error occurred.")
    })
    @GetMapping("/read/me")
    public ResponseEntity<UserEntity> readMyUser() {
        try {
            UserEntity selectedUser = userService.readMyUser();
            return ResponseEntity.ok(selectedUser);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 3. Update my user (Patch)
    @Operation(summary = "Self user update", description = "Allows any user to update its own data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated user returned successfully."),
            @ApiResponse(responseCode = "401", description = "You are not authenticated."),
            @ApiResponse(responseCode = "409", description = "Data provided caused conflict with an existent user."),
            @ApiResponse(responseCode = "500", description = "Unexpected Server error occurred.")
    })
    @PatchMapping("/update")
    public ResponseEntity<UserEntity> updateMyUser(@RequestBody @Valid UserPatchDTO userPatchDTO) {
        try {
            UserEntity updatedUser = userService.updateMyUser(userPatchDTO);
            return ResponseEntity.ok(updatedUser);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 4. Delete a single user (ADMIN only)
    @Operation(summary = "User deletion (ADMIN only)", description = "Allows the ADMIN to delete any user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully."),
            @ApiResponse(responseCode = "400", description = "User not found."),
            @ApiResponse(responseCode = "401", description = "You are not authenticated."),
            @ApiResponse(responseCode = "409", description = "Data provided caused conflict with an existent user."),
            @ApiResponse(responseCode = "500", description = "Unexpected Server error occurred.")
    })
    @DeleteMapping("/delete/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 4.1 Delete my user
    @Operation(summary = "Self user deletion", description = "Allows any user to delete its own data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully."),
            @ApiResponse(responseCode = "401", description = "You are not authenticated."),
            @ApiResponse(responseCode = "500", description = "Unexpected Server error occurred.")
    })
    @DeleteMapping("/delete/my/user")
    public ResponseEntity<Void> deleteMyUser() {
        try {
            userService.deleteMyUser();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
