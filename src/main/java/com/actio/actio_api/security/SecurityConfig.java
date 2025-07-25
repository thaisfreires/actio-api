package com.actio.actio_api.security;

import com.actio.actio_api.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Security configuration class for the application.
 *
 * Configures password encoding, authentication manager,
 * session policy, and security filters including JWT handling.
 * Defines authorization rules and disables CSRF protection for a stateless API.
 */
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    /**
     * Defines the password encoder used throughout the application for user authentication.
     *
     * @return a BCrypt-based password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Builds the authentication manager based on the custom user details service
     * and password encoder.
     *
     * @param http the current {@link HttpSecurity} configuration
     * @param passwordEncoder the configured password encoder
     * @return the authentication manager used by Spring Security
     * @throws Exception if configuration fails
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder)
            throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
        return builder.build();
    }


    /**
     * Configures the CORS settings to allow cross-origin requests from the Angular frontend.
     *
     * @return the {@link CorsConfigurationSource} that defines the CORS policy applied to all endpoints
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.setExposedHeaders(List.of("authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /**
     * Defines the security filter chain for incoming HTTP requests.
     *
     * - Disables CSRF protection (not needed for stateless APIs) <br>
     * - Allows unauthenticated access to endpoints such as login and registration <br>
     * - Requires authentication for all other endpoints <br>
     * - Configures stateless session management <br>
     * - Adds a custom JWT authorization filter before the default authentication filter
     *
     * @param http the {@link HttpSecurity} object
     * @return the configured security filter chain
     * @throws Exception if any configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/api/test/relations").permitAll()
                        .requestMatchers("/api/test/buy-hardcoded").permitAll()
                        .requestMatchers("/users/save").permitAll()
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/stocks/**").permitAll()
                        .requestMatchers("/transactions/buy").hasRole("CLIENT")
                        .requestMatchers("/transactions/sell").hasRole("CLIENT")
                        .requestMatchers("/transactions/getAll").authenticated()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
