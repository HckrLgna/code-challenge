package com.lgnasolutions.backend_challenge.application.services;

import com.lgnasolutions.backend_challenge.domain.dto.FilterStateDTO;
import com.lgnasolutions.backend_challenge.domain.entities.FilterState;
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
        System.out.println("DEBUG filters type: " + filterStateDTO.getFilters().getClass().getName());
        if (!(filterStateDTO.getFilters() instanceof java.util.Map)) {
            throw new IllegalArgumentException("filters must be a Map<String, Object>, not " + filterStateDTO.getFilters().getClass().getName());
        }
        // Depuración: imprime el contenido real del Map
        System.out.println("DEBUG filters content: " + filterStateDTO.getFilters());
        FilterState state = FilterState.builder()
                .userId(userId)
                .filters(filterStateDTO.getFilters())
                .build();
        System.out.println( state.getFilters());
        FilterState saved = filterStateRepository.save(state);
        return FilterStateMapper.toDTO(saved);
    }

    public java.util.List<FilterStateDTO> getFiltersState(UUID userId) {
        return filterStateRepository.getAllFiltersStateByUserId(userId)
                .stream()
                .map(FilterStateMapper::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }
}
