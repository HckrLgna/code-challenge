package com.lgnasolutions.backend_challenge.infraestructure.repositories;

import com.lgnasolutions.backend_challenge.domain.dto.NoteSearchCriteriaDTO;
import com.lgnasolutions.backend_challenge.infraestructure.datasource.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NoteJpaRepository extends JpaRepository<NoteEntity, UUID> {
    List<NoteEntity> findByUserId(UUID userId);

    List<NoteEntity> findByUserIdAndArchived(UUID userId, boolean archived);
    List<NoteEntity> findByUserIdAndArchivedAndTitleContainingIgnoreCase(UUID userId, boolean archived, String title);
    List<NoteEntity> findByUserIdAndTitleContainingIgnoreCase(UUID userId, String title);
}
