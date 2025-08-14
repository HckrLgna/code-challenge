package com.lgnasolutions.backend_challenge.domain.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteSearchCriteriaDTO {
    private UUID userId;
    private String title;
    private Boolean isArchived;
}
