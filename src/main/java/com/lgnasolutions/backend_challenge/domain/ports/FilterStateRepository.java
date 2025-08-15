package com.lgnasolutions.backend_challenge.domain.ports;

import com.lgnasolutions.backend_challenge.domain.entities.FilterState;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FilterStateRepository {
    FilterState save(FilterState state);
    List<FilterState> getAllFiltersStateByUserId(UUID userId);

}
