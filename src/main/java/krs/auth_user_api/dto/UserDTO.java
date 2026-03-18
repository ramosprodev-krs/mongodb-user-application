package krs.auth_user_api.dto;

import jakarta.validation.constraints.*;
import krs.auth_user_api.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotBlank(message = "Provided username is blank.")
    @Size(min = 5, max = 15)
    private String username;

    @NotBlank(message = "Provided password is blank.")
    @Size(min = 5)
    private String password;

    @CPF(message = "Please provide a valid CPF.")
    private String cpf;

    @Email(message = "Please provide a valid E-mail.")
    private String email;

    private UserRole userRole;
}
