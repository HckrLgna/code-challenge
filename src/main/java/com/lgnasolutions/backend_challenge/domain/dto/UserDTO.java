package com.lgnasolutions.backend_challenge.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private UUID roleId;
    private String fullName;
}
