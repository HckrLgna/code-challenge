package com.lgnasolutions.backend_challenge.infraestructure.repositories;

import com.lgnasolutions.backend_challenge.domain.entities.FilterState;
import com.lgnasolutions.backend_challenge.domain.ports.FilterStateRepository;
import com.lgnasolutions.backend_challenge.infraestructure.datasource.FilterStateEntity;
import com.lgnasolutions.backend_challenge.infraestructure.mappers.FilterStateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FilterStateRepositoryImpl implements FilterStateRepository {
    private final FilterStateJpaRepository filterStateJpaRepository;
    @Autowired
    public FilterStateRepositoryImpl(FilterStateJpaRepository filterStateJpaRepository) {
        this.filterStateJpaRepository = filterStateJpaRepository;
    }

    @Override
    public FilterState save(FilterState state) {
        FilterStateEntity entity = FilterStateMapper.toEntity(state);
        System.out.println("filter state repos"+state.getFilters());
        FilterStateEntity savedEntity = filterStateJpaRepository.save(entity);
        System.out.println("filter state repos saved"+savedEntity.getFilters());
        return FilterStateMapper.toDomain(savedEntity);
    }

    @Override
    public List<FilterState> getAllFiltersStateByUserId(UUID userId) {
        return filterStateJpaRepository.getFilterStateEntityListByUserId(userId)
                .stream()
                .map(FilterStateMapper::toDomain)
                .toList();

    }
}
