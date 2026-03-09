package krs.auth_user_api.services;

import krs.auth_user_api.repository.DatabaseRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final DatabaseRepository databaseRepository;

    public CustomUserDetailsService(DatabaseRepository databaseRepository) {
        this.databaseRepository = databaseRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return databaseRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}



