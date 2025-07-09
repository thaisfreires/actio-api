package com.actio.actio_api.security;

import com.actio.actio_api.model.ActioUser;
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

/**
 * Utility class for generating, parsing, and validating JSON Web Tokens (JWT)
 * used in user authentication.
 *
 * This class handles the lifecycle of a JWT — from creation with embedded user claims,
 * to extraction and validation of claims during API request filtering.
 *
 * It uses a symmetric secret key configured via the {@code jwtKey} property,
 * and signs tokens using HS256. The default validity duration for tokens is 60 minutes.
 */
@Component
public class JwtUtil {

    @Value("${jwtKey}")
    private String SECRET;
    private final long ACCESS_TOKEN_VALIDITY_SECONDS = 3600;
    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    private JwtParser jwtParser;
    private SecretKey key;

    /**
     * Initializes the secret key and parser after the bean is constructed.
     */
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
        this.jwtParser = Jwts.parser().verifyWith(key).build();
    }

    /**
     * Creates a signed JWT token with the user's email and role as claims.
     *
     * @param user the user profile to embed in the token
     * @return a signed JWT token
     */
    public String createToken(ActioUser user) {
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

    /**
     * Extracts and validates claims from a JWT token in the request.
     *
     * @param request the HTTP request containing the Authorization header
     * @return the parsed JWT claims if present and valid, or {@code null}
     * @throws ExpiredJwtException if the token has expired
     * @throws Exception for any other invalid token condition
     */
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

    /**
     * Extracts the raw JWT token from the Authorization header, if present.
     *
     * @param request the HTTP request
     * @return the raw token string, or {@code null} if not found or malformed
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    /**
     * Validates whether the provided claims are still valid based on expiration.
     *
     * @param claims the claims extracted from the token
     * @return {@code true} if the token is still valid; {@code false} otherwise
     */
    public boolean validateClaims(Claims claims) {
        return claims.getExpiration().after(new Date());
    }

    /**
     * Retrieves the user's email from the token claims.
     *
     * @param claims the JWT claims
     * @return the subject (user email)
     */
    public String getEmail(Claims claims) {
        return claims.getSubject();
    }

    /**
     * Retrieves the user's email directly from a token string.
     *
     * @param token the full Bearer token string
     * @return the subject (user email)
     */
    public String getEmail(String token) {
        token = token.substring(TOKEN_PREFIX.length());
        Claims claims = parseJwtClaims(token);
        return  claims.getSubject();
    }

    /**
     * (Private) Extracts the role from claims, if present.
     *
     * @param claims the JWT claims
     * @return the role name or {@code null} if not found
     */
    private String getRole(Claims claims) {
        Object raw = claims.get("roles");
        return raw instanceof String ? (String) raw : null;
    }

    private Claims parseJwtClaims(String token) {
        return jwtParser.parseSignedClaims(token).getPayload();
    }
}
