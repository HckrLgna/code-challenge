package com.lgnasolutions.backend_challenge.infraestructure.mappers;

import com.lgnasolutions.backend_challenge.domain.dto.FilterStateDTO;
import com.lgnasolutions.backend_challenge.domain.entities.FilterState;
import com.lgnasolutions.backend_challenge.infraestructure.datasource.FilterStateEntity;

public class FilterStateMapper {
    public static FilterStateDTO toDTO(FilterState state) {
        return FilterStateDTO.builder()
                .id(state.getId())
                .filters(state.getFilters())
                .build();
    }
    public static FilterState toDomain(FilterStateEntity entity) {
        return FilterState.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .filters(entity.getFilters())

                .build();
    }

    public static FilterStateEntity toEntity(FilterState state) {
        return FilterStateEntity.builder()
                .id(state.getId())
                .userId(state.getUserId())
                .filters(state.getFilters())

                .build();
    }
}
