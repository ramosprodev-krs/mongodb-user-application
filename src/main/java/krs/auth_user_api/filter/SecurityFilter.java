package krs.auth_user_api.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import krs.auth_user_api.entity.UserEntity;
import krs.auth_user_api.repository.DatabaseRepository;
import krs.auth_user_api.services.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    // Manually injecting dependencies
    private final TokenService tokenService;
    private final DatabaseRepository databaseRepository;

    public SecurityFilter(TokenService tokenService, DatabaseRepository databaseRepository) {
        this.tokenService = tokenService;
        this.databaseRepository = databaseRepository;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            try {
                String username = tokenService.validateToken(token);
                UserEntity user = this.databaseRepository.findByUsername(username).orElseThrow();
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        filterChain.doFilter(request, response);

    }
}
