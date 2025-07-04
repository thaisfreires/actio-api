package com.actio.actio_api.security;

import com.actio.actio_api.model.UserProfile;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    @Value("${jwtKey}")
    private String SECRET;
    private final long ACCESS_TOKEN_VALIDITY_SECONDS = 3600;
    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    private JwtParser jwtParser;
    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
        this.jwtParser = Jwts.parser().verifyWith(key).build();
    }

    public String createToken(UserProfile user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("name", user.getName());
        claims.put("roles", new String[] { user.getRole().name() });

        Date tokenCreationTime = new Date();
        Date tokenExpiration = new Date(tokenCreationTime.getTime()
                + TimeUnit.MINUTES.toMillis(ACCESS_TOKEN_VALIDITY_SECONDS));

        return Jwts.builder()
                .claims(claims)
                .subject(user.getEmail())
                .issuedAt(tokenCreationTime)
                .expiration(tokenExpiration)
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }

    public Claims resolveClaims(HttpServletRequest request) {
        try {
            String token = resolveToken(request);
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            request.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            request.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public boolean validateClaims(Claims claims) {
        return claims.getExpiration().after(new Date());
    }

    public String getEmail(Claims claims) {
        return claims.getSubject();
    }

    public String getEmail(String token) {
        token = token.substring(TOKEN_PREFIX.length());
        Claims claims = parseJwtClaims(token);
        return  claims.getSubject();
    }

    private String getRole(Claims claims) {
        Object raw = claims.get("roles");
        return raw instanceof String ? (String) raw : null;
    }

    private Claims parseJwtClaims(String token) {
        return jwtParser.parseSignedClaims(token).getPayload();
    }
}
