package com.lgnasolutions.backend_challenge.domain.ports;

import com.lgnasolutions.backend_challenge.domain.dto.NoteSearchCriteriaDTO;
import com.lgnasolutions.backend_challenge.domain.entities.Note;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NoteRepository {
    List<Note> findAll();
    Note create(Note note);
    Optional<Note> findById(UUID id);
    List<Note> findByCriteria(NoteSearchCriteriaDTO criteria);
    Note update(Note note);
    void delete(UUID id);
    void saveAll(List<Note> notes);
}
