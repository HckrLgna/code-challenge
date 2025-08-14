package com.lgnasolutions.backend_challenge.domain.entities;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    private UUID id;
    private UUID userId;
    private String title;
    private String content;
    private boolean archived;

}
