package com.lgnasolutions.backend_challenge.domain.dto;

import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteVersionDTO {
    private UUID id;
    private UUID noteId;
    private String title;
    private String content;
    private int versionNumber;
    private Instant createdAt;
}
