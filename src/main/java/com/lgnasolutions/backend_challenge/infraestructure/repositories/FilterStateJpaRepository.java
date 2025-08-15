package com.lgnasolutions.backend_challenge.infraestructure.repositories;

import com.lgnasolutions.backend_challenge.domain.entities.FilterState;
import com.lgnasolutions.backend_challenge.infraestructure.datasource.FilterStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FilterStateJpaRepository extends JpaRepository<FilterStateEntity, UUID> {

    List<FilterStateEntity> getFilterStateEntityListByUserId(UUID userId);
}
