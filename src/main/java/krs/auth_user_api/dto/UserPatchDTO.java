package krs.auth_user_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPatchDTO {
    private String username;
    private String password;
    private String email;
}
