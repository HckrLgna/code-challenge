package com.lgnasolutions.backend_challenge.infraestructure.repositories;

import com.lgnasolutions.backend_challenge.domain.entities.FilterState;
import com.lgnasolutions.backend_challenge.domain.ports.FilterStateRepository;
import com.lgnasolutions.backend_challenge.infraestructure.datasource.FilterStateEntity;
import com.lgnasolutions.backend_challenge.infraestructure.mappers.FilterStateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
        FilterStateEntity savedEntity = filterStateJpaRepository.save(entity);
        return FilterStateMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<FilterState> findByUserId(UUID userId) {
        return filterStateJpaRepository.findByUserId(userId).map(FilterStateMapper::toDomain);
    }
}
