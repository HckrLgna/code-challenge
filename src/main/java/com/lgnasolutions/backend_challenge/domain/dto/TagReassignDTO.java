package com.lgnasolutions.backend_challenge.domain.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagReassignDTO {
    private List<UUID> noteIds;
    private UUID newTagId;
}
