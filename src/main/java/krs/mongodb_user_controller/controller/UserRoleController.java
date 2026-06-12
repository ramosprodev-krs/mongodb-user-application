package krs.mongodb_user_controller.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import krs.mongodb_user_controller.entity.UserRole;
import krs.mongodb_user_controller.services.UserRoleService;
import org.springframework.http.ResponseEntity;
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
        var roles = userRoleService.promoteToAdmin(userId);
        return ResponseEntity.ok(roles);
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
        var roles = userRoleService.demoteAdminRole(userId);
        return ResponseEntity.ok(roles);
    }


}
