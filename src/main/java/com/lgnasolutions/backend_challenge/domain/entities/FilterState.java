package com.lgnasolutions.backend_challenge.domain.entities;

import lombok.*;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterState {
    private UUID id;
    private UUID userId;
    private Map<String, Object> filters;

}
