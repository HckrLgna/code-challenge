package com.lgnasolutions.backend_challenge.domain.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagAssignNoteDTO {
    private UUID tagId;
    private UUID noteId;
}
