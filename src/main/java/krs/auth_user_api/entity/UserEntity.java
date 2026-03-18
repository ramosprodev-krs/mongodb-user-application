package krs.auth_user_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class UserEntity implements UserDetails {

    @MongoId
    @Schema(type = "string")
    private String id;

    @NotBlank(message = "Provided username is blank.")
    @Size(min = 5, max = 15)
    private String username;

    @JsonIgnore
    @NotBlank(message = "Provided password is blank.")
    @Size(min = 5)
    private String password;

    @CPF(message = "Please provide a valid CPF.")
    @Schema(type = "string", example = "000.000.000-00")
    private String cpf;

    @Email(message = "Please provide a valid E-mail.")
    private String email;

    private LocalDateTime creationDate;

    private Set<UserRole> userRoles = new HashSet<>();

    @JsonIgnore
    @Schema(hidden = true)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (userRoles == null) return List.of();
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .toList();
    }

    @JsonIgnore
    @Schema(hidden = true)
    @Override
    public String getPassword() {
        return this.password;
    }

    @JsonIgnore
    @Schema(hidden = true)
    @Override
    public String getUsername() {
        return this.username;
    }

    @JsonIgnore
    @Schema(hidden = true)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Schema(hidden = true)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Schema(hidden = true)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Schema(hidden = true)
    @Override
    public boolean isEnabled() {
        return true;
    }
}