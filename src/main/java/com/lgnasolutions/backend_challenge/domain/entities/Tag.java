package com.lgnasolutions.backend_challenge.domain.entities;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    private UUID id;
    private UUID userId;
    private String name;
    private String color;
    private Instant createdAt;
}
