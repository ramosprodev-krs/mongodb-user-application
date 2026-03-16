package krs.auth_user_api.services;

import krs.auth_user_api.entity.UserEntity;
import krs.auth_user_api.entity.UserRole;
import krs.auth_user_api.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;

public class UserRoleService {

    private final UserRepository userRepository;

    public UserRoleService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * The UserRoleService class is simple, but very useful for user role controlling.
     * <p>
     * It is meant only to be used by ADMIN, allowing promotion and demotion of other ADMINs.
     **/

    // 1. Admin promotion method
    public Set<UserRole> promoteToAdmin(String id) {
        UserEntity selectedUser = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        var roles = selectedUser.getUserRoles();
        if (!roles.add(UserRole.ADMIN)) {
            throw new UnsupportedOperationException("User is already an ADMIN.");
        }

        userRepository.save(selectedUser);
        return roles;
    }

    // 2. Admin role removal
    public Set<UserRole> demoteAdminRole(String id) {
        UserEntity selectedUser = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        var roles = selectedUser.getUserRoles();
        if (!roles.remove(UserRole.ADMIN)) {
            throw new UnsupportedOperationException("User is not an ADMIN.");
        }

        userRepository.save(selectedUser);
        return roles;
    }
}
