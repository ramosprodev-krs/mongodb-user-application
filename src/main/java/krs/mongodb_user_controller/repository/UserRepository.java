package krs.mongodb_user_controller.repository;

import krs.mongodb_user_controller.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserEntity, String> {
    boolean existsByUsername(String username);
    Optional<UserEntity> findByUsername(String username);
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
}
