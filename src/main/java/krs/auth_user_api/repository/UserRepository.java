package krs.auth_user_api.repository;

import krs.auth_user_api.entity.UserEntity;
import krs.auth_user_api.entity.UserRole;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserEntity, String> {
    boolean existsByUsername(String username);
    Optional<UserEntity> findByUsername(String username);
    boolean existsByRole(UserRole role);
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
}
