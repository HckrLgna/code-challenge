package com.lgnasolutions.backend_challenge.infraestructure.datasource;

import lombok.*;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    @Column(columnDefinition = "jsonb")
    @Convert(converter = PermissionsConverter.class)

    private Map<String, Object> permissions;

}
