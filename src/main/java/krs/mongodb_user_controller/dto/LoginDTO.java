package krs.mongodb_user_controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @NotBlank(message = "Username can't be blank.")
    private String username;

    @NotBlank(message = "Password can't be blank.")
    private String password;
}
