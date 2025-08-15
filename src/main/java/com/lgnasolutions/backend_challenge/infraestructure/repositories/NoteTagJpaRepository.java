package com.lgnasolutions.backend_challenge.infraestructure.repositories;

import com.lgnasolutions.backend_challenge.infraestructure.datasource.NoteTagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NoteTagJpaRepository extends JpaRepository<NoteTagEntity, NoteTagEntity.NoteTagId> {
    List<NoteTagEntity> findByTagId(UUID tagId);
    List<NoteTagEntity> findByNoteId(UUID noteId);
}
