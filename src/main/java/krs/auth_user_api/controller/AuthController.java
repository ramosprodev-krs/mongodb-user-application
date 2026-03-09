package krs.auth_user_api.controller;

import jakarta.validation.Valid;
import krs.auth_user_api.dto.LoginDTO;
import krs.auth_user_api.dto.UserCreationDTO;
import krs.auth_user_api.entity.UserEntity;
import krs.auth_user_api.services.TokenService;
import krs.auth_user_api.services.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    //  Manually injecting dependencies
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager,
                          TokenService tokenService,
                          UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userService = userService;
    }

    // ---LOGIN---
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginDTO loginDTO) {
        var authToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        var authentication = this.authenticationManager.authenticate(authToken);
        var user = (UserEntity) authentication.getPrincipal();
        var token = this.tokenService.generateToken(user);
        return ResponseEntity.ok("User logged in successfully." + "\n" + "Token:\n" + token);
    }

    // ---REGISTER---
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserCreationDTO userCreationDTO,
                                               Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are already logged in. Please log out first.");
        }

        try {
            UserEntity user = this.userService.createUser(userCreationDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("User " + user.getUsername() + " (" + user.getId() + ") created successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }
}
