package krs.auth_user_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
public class RegisterDTO {

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
}
