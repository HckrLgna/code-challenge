package com.lgnasolutions.backend_challenge.infraestructure.repositories;

import com.lgnasolutions.backend_challenge.infraestructure.datasource.NoteVersionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NoteVersionJpaRepository extends JpaRepository<NoteVersionEntity, UUID> {
    List<NoteVersionEntity> findByNoteId(UUID noteId);
}
