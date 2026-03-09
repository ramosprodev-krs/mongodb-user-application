package krs.auth_user_api.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserCreationDTO {

    @NotBlank(message = "Error: User full name cannot be blank.")
    @Size(min = 10, max = 30, message = "Error: User full name is either too short or too long. ")
    private String fullName;

    @NotBlank(message = "Error: Username cannot be blank.")
    @Size(min = 10, max = 30, message = "Error: Username is either too short or too long. ")
    private String username;

    @NotBlank(message = "Error: Password cannot be blank.")
    @Size(min = 12, max = 40)
    private String password;

    @CPF(message = "Error: Insert a valid CPF.")
    private String cpf;

    @Email(message = "Error: Insert a valid E-mail.")
    private String email;

    @NotNull(message = "Error: Age cannot be null")
    @Min(value = 16, message = "Error: Age minimum is 16.")
    @Max(value = 86, message = "Error: Age maximum is 86.")
    private Integer age;

    private LocalDateTime creationDate;
}
