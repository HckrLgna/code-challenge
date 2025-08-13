package com.lgnasolutions.backend_challenge.domain.entities;

import lombok.*;

import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    private UUID id;
    private String name;
    private String description;
    private Map<String,Object> permissions;


}
