package krs.auth_user_api.services;

import jakarta.validation.Valid;
import krs.auth_user_api.dto.AdminUserCreationDTO;
import krs.auth_user_api.dto.AdminUserPatchDTO;
import krs.auth_user_api.entity.UserEntity;
import krs.auth_user_api.repository.DatabaseRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminService {

    // In this service, which only admin shall have access, the methods are focused on CRUD methods primarily
    // but also additional methods that go beyond the user CRUD, that is present in the other file (UserService).

    // Manually injecting dependencies.
    private final DatabaseRepository databaseRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(DatabaseRepository databaseRepository, PasswordEncoder passwordEncoder) {
        this.databaseRepository = databaseRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ---CREATE---
    public UserEntity createUser(@RequestBody @Valid @NotNull AdminUserCreationDTO adminUserCreationDTO) {
        // Validating if the user does not already exist.
        if (this.databaseRepository.existsByUsername(adminUserCreationDTO.getUsername())) {
            throw new IllegalArgumentException("Provided username already taken.");
        }

        // Validating if the CPF has not been taken.
        if (this.databaseRepository.existsByCpf(adminUserCreationDTO.getCpf())) {
            throw new IllegalArgumentException("Provided CPF already taken.");
        }

        // Validating if the E-mail has not been taken
        if (this.databaseRepository.existsByEmail(adminUserCreationDTO.getEmail())) {
            throw new IllegalArgumentException("Provided E-mail already taken.");
        }

        // Encrypting the inserted password.
        String encryptedPassword = passwordEncoder.encode(adminUserCreationDTO.getPassword());

        // Creating entity's object.
        UserEntity newUser = new UserEntity();

        // Setting correspondent data.
        newUser.setFullName(adminUserCreationDTO.getFullName());
        newUser.setUsername(adminUserCreationDTO.getUsername());
        newUser.setPassword(encryptedPassword);
        newUser.setCpf(adminUserCreationDTO.getCpf());
        newUser.setEmail(adminUserCreationDTO.getEmail());
        newUser.setAge(adminUserCreationDTO.getAge());
        newUser.setUserRole(adminUserCreationDTO.getUserRole());
        newUser.setCreationDate(LocalDateTime.now());

        return this.databaseRepository.save(newUser);
    }

    // ---READ---
    public UserEntity readUser(@Valid @PathVariable String id) {
        return this.databaseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        }

    // ---READ(ALL)---
    public List<UserEntity> readAllUsers() {
        return this.databaseRepository.findAll();
    }

    // ---UPDATE(PATCH)---
    public void updateUser(@PathVariable String id,
                                 @RequestBody @Valid @NotNull AdminUserPatchDTO adminUserPatchDTO) {

        UserEntity selectedUser = this.databaseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        // Validating data existence and if not already registered by other users (if unique)
        // 1. Full name
        if (adminUserPatchDTO.getFullName() != null) {
            if (adminUserPatchDTO.getFullName().equals(selectedUser.getFullName())){
                throw new IllegalArgumentException("Provided full name is the same as the current one.");
            }

            selectedUser.setFullName(adminUserPatchDTO.getFullName());
        }

        // 2. Username
        if (adminUserPatchDTO.getUsername() != null) {
            if (this.databaseRepository.existsByUsername(adminUserPatchDTO.getUsername())) {
                throw new IllegalArgumentException("Username already taken.");
            }

            if (adminUserPatchDTO.getUsername().equals(selectedUser.getUsername())){
                throw new IllegalArgumentException("Provided username is the same as the current one.");
            }

            selectedUser.setUsername(adminUserPatchDTO.getUsername());
        }

        // 3. Password
        if (adminUserPatchDTO.getPassword() != null) {
            var newPassword = this.passwordEncoder.encode(adminUserPatchDTO.getPassword());
            selectedUser.setPassword(newPassword);
        }

        // 4. E-mail
        if (adminUserPatchDTO.getEmail() != null) {
            if (this.databaseRepository.existsByEmail(adminUserPatchDTO.getEmail())) {
                throw new IllegalArgumentException("E-mail already taken.");
            }

            if (adminUserPatchDTO.getEmail().equals(selectedUser.getEmail())){
                throw new IllegalArgumentException("Provided e-mail is the same as the current one.");
            }

            selectedUser.setEmail(adminUserPatchDTO.getEmail());
        }

        // 5. Age
        if (adminUserPatchDTO.getAge() != null) {
            if (adminUserPatchDTO.getAge().equals(selectedUser.getAge())){
                throw new IllegalArgumentException("Provided age is the same as the current one.");
            }

            selectedUser.setAge(adminUserPatchDTO.getAge());
        }

        // 6. User role
        if (adminUserPatchDTO.getUserRole() != null) {
            if (adminUserPatchDTO.getUserRole().equals(selectedUser.getUserRole())){
                throw new IllegalArgumentException("Provided role is the same as the current one.");
            }
            selectedUser.setUserRole(adminUserPatchDTO.getUserRole());
        }

        // Finishing the method by saving user new data
        this.databaseRepository.save(selectedUser);
    }

    // ---DELETE---
    public void deleteUser(@Valid @PathVariable String id) {
        // Basic ID existence validation
        if (!this.databaseRepository.existsById(id)) {
            throw new IllegalArgumentException("User has not been found.");
        }

        this.databaseRepository.deleteById(id);
    }

}
