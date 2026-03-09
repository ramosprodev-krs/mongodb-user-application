package krs.auth_user_api.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import krs.auth_user_api.dto.AdminUserCreationDTO;
import krs.auth_user_api.dto.AdminUserPatchDTO;
import krs.auth_user_api.entity.UserEntity;
import krs.auth_user_api.services.AdminService;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    // Manually injecting dependencies.
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // ---CREATE---
    @PostMapping("users")
    public ResponseEntity<String> createUser(@RequestBody @Valid @NotNull AdminUserCreationDTO adminUserCreationDTO) {
        try {
            UserEntity user = this.adminService.createUser(adminUserCreationDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("User " + user.getUsername() + " (" + user.getId() + ") created successfully.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred");
        } catch (IllegalArgumentException e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    // ---READ---
    @GetMapping("users/read")
    ResponseEntity<?> readAllUsers() {
        try {
            List<UserEntity> usersList = this.adminService.readAllUsers();
            return ResponseEntity.ok(usersList);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

    // ---READ(all)---
    @GetMapping("users/read/{id}")
    ResponseEntity<UserEntity> readUser(@PathVariable String id) {
        try {
            UserEntity selectedUser = this.adminService.readUser(id);
            return ResponseEntity.ok(selectedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // ---UPDATE(PATCH)---
    @PatchMapping("users/patch/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody @Valid @NotNull AdminUserPatchDTO adminUserPatchDTO) {
        try {
            this.adminService.updateUser(id, adminUserPatchDTO);
            return ResponseEntity.ok("User updated successfully.");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error occurred.");
        }
    }

    // ---DELETE---
    @DeleteMapping("users/delete/{id}")
    public ResponseEntity<String> deleteUser(@Valid @PathVariable String id) {
        try {
            this.adminService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User has been successfully deleted");
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
