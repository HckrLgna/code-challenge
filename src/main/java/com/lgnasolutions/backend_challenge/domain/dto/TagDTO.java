package com.lgnasolutions.backend_challenge.domain.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO {
    private UUID id;
    private UUID userId;
    private String name;
    private String color;
}
