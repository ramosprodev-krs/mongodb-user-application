package krs.mongodb_user_controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPatchDTO {
    private String username;
    private String password;
    private String email;
}
