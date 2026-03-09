package krs.auth_user_api.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import krs.auth_user_api.dto.UserPatchDTO;
import krs.auth_user_api.entity.UserEntity;
import krs.auth_user_api.services.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    // Manually injecting dependencies.
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ---READ---
    @GetMapping("/read/{id}")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<?> readUser(@Valid @PathVariable String id) {
        try {
            UserEntity selectedUser = this.userService.readUser(id);
            return ResponseEntity.ok(selectedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // ---UPDATE(PATCH)---
    @PatchMapping("/update/{id}")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<String> updateUser(@Valid @PathVariable String id,
                                             @RequestBody@Valid @NotNull UserPatchDTO userPatchDTO) {

        try {
            this.userService.updateUser(id, userPatchDTO);
            return ResponseEntity.ok("User updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // ---DELETE---
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("#id == authentication.principal.id")
    public ResponseEntity<String> deleteUser(@Valid @PathVariable String id) {
        try {
            this.userService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User deleted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

