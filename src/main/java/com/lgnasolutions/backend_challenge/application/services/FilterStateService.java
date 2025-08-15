package com.lgnasolutions.backend_challenge.application.services;

import com.lgnasolutions.backend_challenge.domain.dto.FilterStateDTO;
import com.lgnasolutions.backend_challenge.domain.entities.FilterState;
import com.lgnasolutions.backend_challenge.domain.exceptions.CustomException;
import com.lgnasolutions.backend_challenge.domain.ports.FilterStateRepository;
import com.lgnasolutions.backend_challenge.infraestructure.mappers.FilterStateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FilterStateService {
    private final FilterStateRepository filterStateRepository;
    public FilterStateDTO saveState(UUID userId, FilterStateDTO filterStateDTO) {
        System.out.println(filterStateDTO.getFilters());
        FilterState state = FilterState.builder()
                .userId(userId)
                .filters(filterStateDTO.getFilters())
                .build();
        FilterState saved = filterStateRepository.save(state);
        return FilterStateMapper.toDTO(saved);
    }

    public FilterStateDTO getState(UUID userId) {
        return filterStateRepository.findByUserId(userId)
                .map(FilterStateMapper::toDTO)
                .orElseThrow( () -> new CustomException("Filter state not found for user: " + userId));
    }
}
