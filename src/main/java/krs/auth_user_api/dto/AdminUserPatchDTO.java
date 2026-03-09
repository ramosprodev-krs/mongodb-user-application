package krs.auth_user_api.dto;

import jakarta.validation.constraints.*;
import krs.auth_user_api.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AdminUserPatchDTO {

    @Size(min = 10, max = 30, message = "Error: User full name is either too short or too long. ")
    private String fullName;

    @Size(min = 10, max = 30, message = "Error: Username is either too short or too long. ")
    private String username;

    @Size(min = 12, max = 40)
    private String password;

    @Email(message = "Error: Insert a valid E-mail.")
    private String email;

    @Min(value = 16, message = "Error: Age minimum is 16.")
    @Max(value = 86, message = "Error: Age maximum is 86.")
    private Integer age;

    private UserRole userRole;
}
