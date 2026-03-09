package krs.auth_user_api.services;

import jakarta.validation.Valid;
import krs.auth_user_api.dto.UserCreationDTO;
import krs.auth_user_api.dto.UserPatchDTO;
import krs.auth_user_api.entity.UserEntity;
import krs.auth_user_api.entity.UserRole;
import krs.auth_user_api.repository.DatabaseRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import java.time.LocalDateTime;

@Service
public class UserService {

    // Manually injecting dependencies
    private final DatabaseRepository databaseRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(DatabaseRepository databaseRepository, BCryptPasswordEncoder passwordEncoder){
        this.databaseRepository = databaseRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // CRUD methods follow next:
    // ---CREATE---
    public UserEntity createUser(@RequestBody @Valid @NotNull UserCreationDTO userCreationDTO) {
        // Validating if the user does not already exist.
        if (this.databaseRepository.existsByUsername(userCreationDTO.getUsername())) {
            throw new IllegalArgumentException("User with the provided username already exists.");
        }

        if (this.databaseRepository.existsByCpf(userCreationDTO.getCpf())) {
            throw new IllegalArgumentException("User with the provided CPF already exists.");
        }

        if (this.databaseRepository.existsByEmail(userCreationDTO.getEmail())) {
            throw new IllegalArgumentException("User with the provided E-mail already exists.");
        }

        // Encrypting the inserted password.
        String encryptedPassword = passwordEncoder.encode(userCreationDTO.getPassword());

        // Creating entity's object.
        UserEntity newUser = new UserEntity();

        // Setting correspondent data.
        newUser.setFullName(userCreationDTO.getFullName());
        newUser.setUsername(userCreationDTO.getUsername());
        newUser.setPassword(encryptedPassword);
        newUser.setCpf(userCreationDTO.getCpf());
        newUser.setEmail(userCreationDTO.getEmail());
        newUser.setAge(userCreationDTO.getAge());
        newUser.setUserRole(UserRole.USER);
        newUser.setCreationDate(LocalDateTime.now());

        return this.databaseRepository.save(newUser);
    }

    // ---READ---
    public UserEntity readUser(@Valid @PathVariable String id) {
        return this.databaseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
    }

    // ---UPDATE(PATCH)---
    public void updateUser(@PathVariable @Valid String id,
                           @RequestBody @Valid @NotNull UserPatchDTO userPatchDTO) {
        UserEntity selectedUser = this.databaseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Validating data existence and if not already registered by other users (if unique)
        // 1. Full name
        if (userPatchDTO.getFullName() != null) {
            if (userPatchDTO.getFullName().equals(selectedUser.getFullName())){
                throw new IllegalArgumentException("Provided full name is the same as the current one.");
            }

            selectedUser.setFullName(userPatchDTO.getFullName());
        }

        // 2. Username
        if (userPatchDTO.getUsername() != null) {
            if (this.databaseRepository.existsByUsername(userPatchDTO.getUsername())) {
                throw new IllegalArgumentException("Username already taken.");
            }

            if (userPatchDTO.getUsername().equals(selectedUser.getUsername())){
                throw new IllegalArgumentException("Provided username is the same as the current one.");
            }

            selectedUser.setUsername(userPatchDTO.getUsername());
        }

        // 3. Password
        if (userPatchDTO.getPassword() != null) {
            var newPassword = this.passwordEncoder.encode(userPatchDTO.getPassword());
            selectedUser.setPassword(newPassword);
        }

        // 4. E-mail
        if (userPatchDTO.getEmail() != null) {
            if (this.databaseRepository.existsByEmail(userPatchDTO.getEmail())) {
                throw new IllegalArgumentException("E-mail already taken.");
            }

            if (userPatchDTO.getEmail().equals(selectedUser.getEmail())){
                throw new IllegalArgumentException("Provided e-mail is the same as the current one.");
            }

            selectedUser.setEmail(userPatchDTO.getEmail());
        }

        // 5. Age
        if (userPatchDTO.getAge() != null) {
            if (userPatchDTO.getAge().equals(selectedUser.getAge())){
                throw new IllegalArgumentException("Provided age is the same as the current one.");
            }

            selectedUser.setAge(userPatchDTO.getAge());
        }


        // Finishing the method by saving user new data
        this.databaseRepository.save(selectedUser);
    }

    // ---DELETE---
    public void deleteUser(@Valid @PathVariable String id) {
        // Basic ID existence validation
        if (!this.databaseRepository.existsById(id)) {
            throw new IllegalArgumentException("The provided ID has not been found.");
        }

        this.databaseRepository.deleteById(id);
    }

}
