package com.lgnasolutions.backend_challenge.domain.ports;

import com.lgnasolutions.backend_challenge.domain.entities.NoteVersion;

import java.util.List;
import java.util.UUID;

public interface NoteVersionRepository {
    NoteVersion save(NoteVersion version);
    List<NoteVersion> findByNoteId(UUID noteId);
    NoteVersion findById(UUID id);
}
