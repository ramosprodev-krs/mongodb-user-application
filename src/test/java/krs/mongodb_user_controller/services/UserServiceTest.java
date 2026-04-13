package krs.mongodb_user_controller.services;

import krs.mongodb_user_controller.dto.UserDTO;
import krs.mongodb_user_controller.dto.UserPatchDTO;
import krs.mongodb_user_controller.entity.UserEntity;
import krs.mongodb_user_controller.entity.UserRole;
import krs.mongodb_user_controller.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    /** For this test, we'll be following the AAA pattern:
     * <p>
     * -Arrange
     * <p>
     * -Act
     * <p>
     * -Assert
     **/

    @InjectMocks
    UserService service;

    @Mock
    UserRepository repository;

    @Mock
    BCryptPasswordEncoder encoder;

    @Nested
    class createUser {

        @Test
        @DisplayName("Should create a user successfully")
        void shouldCreateUser() {
            // Arrange
            var userDTO = new UserDTO();
            userDTO.setUsername("test_user");
            userDTO.setPassword("12345");
            userDTO.setEmail("testuser@email.com");
            userDTO.setCpf("078.857.370-55");
            userDTO.setUserRole(UserRole.USER);

            UserEntity expectedUser = new UserEntity();
            expectedUser.setId("1");
            expectedUser.setUsername("test_user");
            expectedUser.setPassword("12345");
            expectedUser.setEmail("testuser@email.com");
            expectedUser.setCpf("078.857.370-55");
            expectedUser.setCreationDate(LocalDateTime.now());


            doReturn(false).when(repository).existsByUsername(userDTO.getUsername());
            doReturn(false).when(repository).existsByCpf(userDTO.getCpf());
            doReturn(false).when(repository).existsByEmail(userDTO.getEmail());
            doReturn("12345").when(encoder).encode(userDTO.getPassword());
            doReturn(expectedUser).when(repository).save(any(UserEntity.class));

            // Act
            UserEntity result = service.createUser(userDTO);

            // Assert
            assertNotNull(result);
            assertEquals(expectedUser.getPassword(), result.getPassword());
            assertEquals(expectedUser.getUsername(), userDTO.getUsername());
            assertEquals(expectedUser.getEmail(), userDTO.getEmail());
            assertEquals(expectedUser.getCpf(), userDTO.getCpf());
        }

        @Test
        void shouldThrowDataIntegrityViolationException() {
            // Arrange
            var userDTO = new UserDTO();
            userDTO.setUsername("test_user");

            doReturn(true).when(repository).existsByUsername(userDTO.getUsername());

            // Act & Assert
            assertThrows(DataIntegrityViolationException.class, () -> service.createUser(userDTO));

            // Verify
            verify(repository, never()).save(any());
        }
    }

    @Nested
    class readUser {

            @Test
            void shouldReadUser() {
                // Arrange
                var expectedUser = new UserEntity();
                expectedUser.setId("1");

                doReturn(Optional.of(expectedUser)).when(repository).findById("1");

                // Act
                var result = service.readUser("1");

                // Assert
                assertNotNull(result);
                assertEquals(expectedUser.getId(), result.getId());

                // Verify
                verify(repository, times(1)).findById("1");
            }

            @Test
            void shouldThrowUsernameNotFoundException() {
                // Arrange
                var expectedUser = new UserEntity();
                expectedUser.setId("1");

                doThrow(new UsernameNotFoundException("User not found.")).when(repository).findById("1");

                // Act & Assert
                assertThrows(UsernameNotFoundException.class, () -> service.readUser("1"));

                // Verify
                verify(repository, times(1)).findById("1");
            }
    }

    @Nested
    class updateMyUser {

        @Test
        void shouldUpdateMyUser() {
            // Arrange
            var userPatchDTO = new UserPatchDTO();
            userPatchDTO.setUsername("updated_user");
            userPatchDTO.setPassword("new_password");
            userPatchDTO.setEmail("updated.email@email.com");

            var expectedUser = new UserEntity();
            expectedUser.setId("1");
            expectedUser.setUsername("updated_user");
            expectedUser.setPassword("encrypted");
            expectedUser.setEmail("updated.email@email.com");

            var oldUser = new UserEntity();
            oldUser.setId("1");
            oldUser.setUsername("old_username");

            var securityContext = mock(SecurityContext.class);
            var authentication = mock(Authentication.class);

            doReturn(authentication).when(securityContext).getAuthentication();
            doReturn("old_username").when(authentication).getName();
            SecurityContextHolder.setContext(securityContext);
            doReturn(Optional.of(oldUser)).when(repository).findByUsername("old_username");

            doReturn(false).when(repository).existsByUsername(userPatchDTO.getUsername());
            doReturn("encrypted_password").when(encoder).encode(userPatchDTO.getPassword());
            doReturn(false).when(repository).existsByEmail(userPatchDTO.getEmail());
            doReturn(expectedUser).when(repository).save(any(UserEntity.class));

            // Act
            var result = service.updateMyUser(userPatchDTO);

            // Assert
            assertNotNull(result);
            assertEquals(result.getUsername(), userPatchDTO.getUsername());
            assertNotEquals(result.getPassword(), userPatchDTO.getPassword());
            assertEquals(result.getEmail(), userPatchDTO.getEmail());

            // Verify
            verify(repository, times(1)).findByUsername("old_username");
            verify(encoder, times(1)).encode(any());
        }

    }

    @Nested
    class deleteUser {

        @Test
        void shouldDeleteUser() {
            // Arrange
            String userId = "123";

            doReturn(true).when(repository).existsById(userId);

            // Act
            service.deleteUser(userId);

            // Assert & Verify
            verify(repository, times(1)).deleteById(userId);
        }

        @Test
        void shouldDeleteMyUser() {
            // Arrange
            var oldUser = new UserEntity();
            oldUser.setId("123");
            oldUser.setUsername("old_user");

            SecurityContext securityContext = mock(SecurityContext.class);
            Authentication authentication = mock(Authentication.class);

            doReturn(authentication).when(securityContext).getAuthentication();
            doReturn("old_user").when(authentication).getName();
            SecurityContextHolder.setContext(securityContext);
            doReturn(Optional.of(oldUser)).when(repository).findByUsername("old_user");

            // Act
            service.deleteMyUser();

            // Assert & Verify
            verify(repository, times(1)).delete(oldUser);

        }
    }

}