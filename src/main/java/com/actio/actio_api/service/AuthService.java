package com.actio.actio_api.service;

import com.actio.actio_api.model.UserProfile;
import com.actio.actio_api.model.request.LoginRequest;
import com.actio.actio_api.model.response.LoginResponse;
import com.actio.actio_api.repository.UserProfileRepository;
import com.actio.actio_api.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserProfileRepository repository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest request){
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        String email = authentication.getName();
        UserProfile user = this.findByEmail(request.getEmail());
        String token = jwtUtil.createToken(user);
        return LoginResponse
                .builder()
                .email(email)
                .token(token)
                .build();
    }

    private  UserProfile findByEmail(String email){
        return repository.findByEmail(email).orElseThrow();
    }
}
