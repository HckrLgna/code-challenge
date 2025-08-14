package com.lgnasolutions.backend_challenge.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {
    private UUID id;

    private UUID userId;
    @NotBlank
    @Size(min = 8, max = 50)
    private String title;
    private String content;
    private boolean isArchived;
}
