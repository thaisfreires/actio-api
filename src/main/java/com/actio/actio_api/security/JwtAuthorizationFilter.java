package com.actio.actio_api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JWT authentication filter that intercepts requests to validate and authorize access tokens.
 *
 * This filter extracts JWT tokens from incoming HTTP requests, validates them using {@link JwtUtil},
 * and populates the Spring Security context with authenticated user details and roles.
 *
 * If the token is invalid, expired, malformed, or unsupported, the filter generates a structured
 * JSON error response without proceeding to the downstream filters.
 */
@Configuration
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final ObjectMapper mapper;
    private final JwtUtil jwtUtil;

    /**
     * Intercepts each HTTP request to validate and process the JWT token if present.
     *
     * @param request     incoming HTTP request
     * @param response    outgoing HTTP response
     * @param filterChain the filter chain to continue processing if authentication succeeds
     * @throws ServletException if filter processing fails
     * @throws IOException      if writing to the response fails
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        Map<String, Object> errorDetails = new HashMap<>();

        try {
            String accessToken = jwtUtil.resolveToken(request);
            if (accessToken == null) {
                filterChain.doFilter(request, response);
                return;
            }

            Claims claims = jwtUtil.resolveClaims(request);

            if (claims != null && jwtUtil.validateClaims(claims)) {
                String email = claims.getSubject();
                List<String> roles = claims.get("roles", List.class);
                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .toList();

                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(email, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (ExpiredJwtException ex) {
            buildErrorResponse(response, HttpStatus.UNAUTHORIZED, "Token expired", ex.getMessage());

        } catch (UnsupportedJwtException ex) {
            buildErrorResponse(response, HttpStatus.BAD_REQUEST, "Unsupported token", ex.getMessage());

        } catch (MalformedJwtException ex) {
            buildErrorResponse(response, HttpStatus.BAD_REQUEST, "Malformed token", ex.getMessage());

        } catch (IllegalArgumentException ex) {
            buildErrorResponse(response, HttpStatus.BAD_REQUEST, "Token claims string is empty", ex.getMessage());

        } catch (Exception ex) {
            buildErrorResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, "Internal authentication error", ex.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Constructs and writes a standardized JSON error response to the client.
     *
     * @param response the HTTP response
     * @param status   the HTTP status to return
     * @param message  high-level description of the error
     * @param detail   technical or specific error message
     * @throws IOException if writing the response body fails
     */
    private void buildErrorResponse(HttpServletResponse response, HttpStatus status, String message, String detail) throws IOException {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", Instant.now());
        error.put("status", status.value());
        error.put("error", status.getReasonPhrase());
        error.put("message", message);
        error.put("details", detail);

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getWriter(), error);
    }


}
