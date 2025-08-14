package com.lgnasolutions.backend_challenge.application.services;

import com.lgnasolutions.backend_challenge.domain.dto.AuthRequestDTO;
import com.lgnasolutions.backend_challenge.domain.dto.AuthResponseDTO;
import com.lgnasolutions.backend_challenge.domain.dto.UserDTO;
import com.lgnasolutions.backend_challenge.domain.entities.Role;
import com.lgnasolutions.backend_challenge.domain.entities.User;
import com.lgnasolutions.backend_challenge.domain.exceptions.CustomException;
import com.lgnasolutions.backend_challenge.domain.ports.RoleRepository;
import com.lgnasolutions.backend_challenge.domain.ports.UserRepository;
import com.lgnasolutions.backend_challenge.infraestructure.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthResponseDTO login(AuthRequestDTO request) {
        User user = userRepository.findUserByEmail(request.getEmail()).orElseThrow(
                () -> new CustomException("Invalid credentials")
        );

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException("Invalid credentials");
        }
        String token = jwtProvider.generateToken(user.getId());
        return AuthResponseDTO.builder().token(token).build();
    }

    public AuthResponseDTO register(UserDTO dto){
        if(userRepository.findUserByEmail(dto.getEmail()).isPresent()) {
            throw new CustomException("Email already registered");
        }

        Role role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new CustomException("Role not found"));
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        User user = User.builder()
                .email(dto.getEmail())
                .password(encodedPassword)
                .role(role)
                .build();
        User savedUser = userRepository.registerUser(user);
        String token = jwtProvider.generateToken(savedUser.getId());
        return AuthResponseDTO.builder().token(token).build();
    }
}
