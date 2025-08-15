package com.lgnasolutions.backend_challenge.infraestructure.repositories;

import com.lgnasolutions.backend_challenge.infraestructure.datasource.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TagJpaRepository extends JpaRepository<TagEntity, UUID> {
    List<TagEntity> findByUserId(UUID userId);

    Optional<TagEntity> findByUserIdAndName(UUID userId, String name);

}
