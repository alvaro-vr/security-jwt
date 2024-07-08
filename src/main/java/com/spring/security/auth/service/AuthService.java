package com.spring.security.auth.service;

import com.spring.security.auth.dto.AuthRequest;
import com.spring.security.auth.dto.AuthResponse;
import com.spring.security.auth.exceptions.UserAlreadyExistsException;
import com.spring.security.enums.Role;
import com.spring.security.persistence.entities.UserEntity;
import com.spring.security.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public AuthResponse register(AuthRequest request){
        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .roles(Set.of(Role.ROLE_USER))
                .build();
        try {
            this.repository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new UserAlreadyExistsException("Username '"+ request.getUsername() +"' already exists");
        }

        return new AuthResponse(user.getUsername(), "User "+ user.getUsername()+" registered");
    }

}
