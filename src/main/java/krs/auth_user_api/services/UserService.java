package krs.auth_user_api.services;

import krs.auth_user_api.dto.RegisterDTO;
import krs.auth_user_api.dto.UserDTO;
import krs.auth_user_api.dto.UserPatchDTO;
import krs.auth_user_api.entity.UserEntity;
import krs.auth_user_api.entity.UserRole;
import krs.auth_user_api.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    // Manually injecting dependencies
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private static final String NOTFOUNDMESSAGE = "User not found.";


    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * The UserService class is the core of this application, managing user lifecycle and business rules.
     * <p>
     * It implements the UserDetailsService interface to integrate with Spring Security via the loadUserByUsername()
     * method, ensuring the JWT authentication flow works correctly.
     * <p>
     * Beyond security, the class follows the CRUD pattern.
     **/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    // CRUD methods:

    // 1. Create user (ADMIN only)
    public UserEntity createUser(UserDTO userDTO) {
        // Validating if the user does not already exist.
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new DataIntegrityViolationException("Username already taken.");
        }

        if (userRepository.existsByCpf(userDTO.getCpf())) {
            throw new DataIntegrityViolationException("CPF already registered.");
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new DataIntegrityViolationException("E-mail already registered.");
        }

        // Encrypting the provided password
        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());

        // Creating new user entity object
        UserEntity newUser = new UserEntity();

        // Setting correspondent data
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(encryptedPassword);
        newUser.setCpf(userDTO.getCpf());
        newUser.setEmail(userDTO.getEmail());
        newUser.getUserRoles().add(userDTO.getUserRole());
        newUser.setCreationDate(LocalDateTime.now());

        return userRepository.save(newUser);
    }

    // 1.1 Register user
    public UserEntity registerUser(RegisterDTO registerDTO) {
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new DataIntegrityViolationException("Username already taken.");
        }

        if (userRepository.existsByCpf(registerDTO.getCpf())) {
            throw new DataIntegrityViolationException("CPF already registered.");
        }

        if (userRepository.existsByEmail(registerDTO.getEmail())) {
            throw new DataIntegrityViolationException("E-mail already registered.");
        }

        // Encrypting the provided password
        String encryptedPassword = passwordEncoder.encode(registerDTO.getPassword());

        // Creating new user entity object
        UserEntity newUser = new UserEntity();

        // Setting correspondent data
        newUser.setUsername(registerDTO.getUsername());
        newUser.setPassword(encryptedPassword);
        newUser.setCpf(registerDTO.getCpf());
        newUser.setEmail(registerDTO.getEmail());
        newUser.getUserRoles().add(UserRole.USER);
        newUser.setCreationDate(LocalDateTime.now());

        return userRepository.save(newUser);
    }

    // 2. Read all users (ADMIN only)
    public List<UserEntity> readAllUsers() {
        return userRepository.findAll();
    }

    // 2.1 Read single user (ADMIN only)
    public UserEntity readUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException(NOTFOUNDMESSAGE));
    }

    // 2.2 Read my user
    public UserEntity readMyUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(NOTFOUNDMESSAGE));
    }

    // 3. Update my user
    public UserEntity updateMyUser(UserPatchDTO userPatchDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity selectedUser =  userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(NOTFOUNDMESSAGE));

        // Validating data existence and if not already registered by other users (if unique)

        // 1. Username
        if (userPatchDTO.getUsername() != null) {
            if (userRepository.existsByUsername(userPatchDTO.getUsername())) {
                throw new DataIntegrityViolationException("Username already taken.");
            }

            if (userPatchDTO.getUsername().equals(selectedUser.getUsername())){
                throw new DataIntegrityViolationException("Provided username is the same as the current one.");
            }

            selectedUser.setUsername(userPatchDTO.getUsername());
        }

        // 2. Password
        if (userPatchDTO.getPassword() != null) {
            var newPassword = passwordEncoder.encode(userPatchDTO.getPassword());
            selectedUser.setPassword(newPassword);
        }

        // 3. E-mail
        if (userPatchDTO.getEmail() != null) {
            if (userRepository.existsByEmail(userPatchDTO.getEmail())) {
                throw new DataIntegrityViolationException("E-mail already taken.");
            }

            if (userPatchDTO.getEmail().equals(selectedUser.getEmail())){
                throw new DataIntegrityViolationException("Provided e-mail is the same as the current one.");
            }

            selectedUser.setEmail(userPatchDTO.getEmail());
        }

        // Finishing the method by saving user new data
        return userRepository.save(selectedUser);
    }

    // 4. Delete single user (ADMIN only)
    public void deleteUser(String userId) {
        // Basic ID existence validation
        if (!userRepository.existsById(userId)) {
            throw new UsernameNotFoundException("The provided ID has not been found.");
        }

        userRepository.deleteById(userId);
    }

    // 4.1 Delete my user
    public void deleteMyUser() {
        // Basic ID existence validation
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity selectedUser =  userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(NOTFOUNDMESSAGE));


        userRepository.delete(selectedUser);
    }
}
