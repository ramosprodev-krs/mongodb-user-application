package krs.auth_user_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import krs.auth_user_api.entity.UserRole;
import krs.auth_user_api.services.UserRoleService;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/roles")
public class UserRoleController {

    private final UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Operation(summary = "User promotion to ADMIN (ADMIN only)", description = "Allows the ADMIN to promote a user to ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully promoted."),
            @ApiResponse(responseCode = "401", description = "You are not authenticated."),
            @ApiResponse(responseCode = "403", description = "Request access denied."),
            @ApiResponse(responseCode = "407", description = "User is already an ADMIN."),
            @ApiResponse(responseCode = "500", description = "Unexpected Server error occurred.")
    })
    @PatchMapping("/promote/admin/{userId}")
    public ResponseEntity<Set<UserRole>> promoteToAdmin(@PathVariable String userId) {
        try {
            var roles = userRoleService.promoteToAdmin(userId);
            return ResponseEntity.ok(roles);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @Operation(summary = "User ADMIN demotion (ADMIN only)", description = "Allows the ADMIN to demote an ADMIN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully demoted."),
            @ApiResponse(responseCode = "401", description = "You are not authenticated."),
            @ApiResponse(responseCode = "403", description = "Request access denied."),
            @ApiResponse(responseCode = "407", description = "User is not an ADMIN."),
            @ApiResponse(responseCode = "500", description = "Unexpected Server error occurred.")
    })
    @DeleteMapping("/demote/admin/{userId}")
    public ResponseEntity<Set<UserRole>> demoteAdminRole(@PathVariable String userId) {
        try {
            var roles = userRoleService.demoteAdminRole(userId);
            return ResponseEntity.ok(roles);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


}
