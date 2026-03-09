package krs.auth_user_api.controller;

import jakarta.validation.Valid;
import krs.auth_user_api.dto.AdminUserCreationDTO;
import krs.auth_user_api.entity.UserEntity;
import krs.auth_user_api.entity.UserRole;
import krs.auth_user_api.repository.DatabaseRepository;
import krs.auth_user_api.services.AdminService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequestMapping("/setup")
public class SetupController {
    // Manually injecting dependencies
    private final AdminService adminService;
    private final DatabaseRepository databaseRepository;

    public SetupController(AdminService adminService, DatabaseRepository databaseRepository) {
        this.adminService = adminService;
        this.databaseRepository = databaseRepository;
    }

    @PostMapping("/admin")
    public ResponseEntity<String> createInitialAdmin(@RequestBody @Valid @NotNull AdminUserCreationDTO adminUserCreationDTO) {
        if (this.databaseRepository.existsByRole(UserRole.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Setup endpoint is disabled: an admin user already exists.");
        }

        if (!adminUserCreationDTO.getUserRole().equals(UserRole.ADMIN)) {
            return ResponseEntity.badRequest().body("Please insert the valid role: \"ADMIN\"");
        }

        UserEntity createdUser = this.adminService.createUser(adminUserCreationDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User " + createdUser.getUsername() + " (" + createdUser.getId() + ") " + "created successfully.");

    }
}
