    package krs.auth_user_api.services;

    import io.jsonwebtoken.Jwts;
    import io.jsonwebtoken.SignatureAlgorithm;
    import io.jsonwebtoken.security.Keys;
    import krs.auth_user_api.entity.UserEntity;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Service;
    import java.security.Key;
    import java.util.Date;

    @Service
    public class TokenService {

        @Value("${jwt.secret}")
        private String secretKey;

        @Value("${jwt.expiration}")
        private Long expirationTime;

        private Key getSigningKey() {
            return Keys.hmacShaKeyFor(secretKey.getBytes());
        }

        public String generateToken(UserEntity userEntity) {
            return Jwts.builder()
                    .setSubject(userEntity.getUsername())
                    .claim("role", userEntity.getUserRole().getRole())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                    .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                    .compact();
        }

        public String validateToken(String token) {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        }
    }
