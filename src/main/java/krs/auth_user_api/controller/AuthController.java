package krs.auth_user_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import krs.auth_user_api.dto.LoginDTO;
import krs.auth_user_api.dto.RegisterDTO;
import krs.auth_user_api.entity.UserEntity;
import krs.auth_user_api.services.TokenService;
import krs.auth_user_api.services.UserService;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
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

    // 1. Login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginDTO loginDTO) {
        var authToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        var authentication = this.authenticationManager.authenticate(authToken);
        var user = (UserEntity) authentication.getPrincipal();
        var token = this.tokenService.generateToken(user);
        return ResponseEntity.ok(token);
    }

    // 2. Register
    @Operation(summary = "User registration", description = "Allows anyone to create a new user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully."),
            @ApiResponse(responseCode = "409", description = "Data provided caused conflict with an existent user."),
            @ApiResponse(responseCode = "500", description = "Unexpected Server error occurred.")
    })
    @PostMapping("/register")
    public ResponseEntity<UserEntity> registerUser(@RequestBody @Valid RegisterDTO registerDTO) {
        try {
            UserEntity createdUser = userService.registerUser(registerDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
