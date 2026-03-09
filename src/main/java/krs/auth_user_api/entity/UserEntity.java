    package krs.auth_user_api.entity;

    import lombok.AllArgsConstructor;
    import lombok.Getter;
    import lombok.NoArgsConstructor;
    import lombok.Setter;
    import org.springframework.data.mongodb.core.mapping.Document;
    import org.springframework.data.mongodb.core.mapping.MongoId;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.userdetails.UserDetails;

    import java.time.LocalDateTime;
    import java.util.Collection;
    import java.util.List;

    @Document(collection = "authenticated-users")

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor

    public class UserEntity implements UserDetails {

        // The attributes are validated directly inside the "UserDTO" class.

        @MongoId
        private String id;
        private String fullName;
        private String username;
        private String password;
        private String cpf;
        private String email;
        private Integer age;
        private LocalDateTime creationDate;
        private UserRole userRole;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return List.of(new SimpleGrantedAuthority(userRole.getRole()));
        }

        @Override
        public boolean isAccountNonExpired() {
            return UserDetails.super.isAccountNonExpired();
        }

        @Override
        public boolean isAccountNonLocked() {
            return UserDetails.super.isAccountNonLocked();
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return UserDetails.super.isCredentialsNonExpired();
        }

        @Override
        public boolean isEnabled() {
            return UserDetails.super.isEnabled();
        }
    }
