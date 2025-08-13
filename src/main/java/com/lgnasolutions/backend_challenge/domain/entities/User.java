package com.lgnasolutions.backend_challenge.domain.entities;

import com.lgnasolutions.backend_challenge.config.Validators;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User {
    private UUID id;
    private String email;
    private String password;
    private String fullName;
    private Role role;
}
