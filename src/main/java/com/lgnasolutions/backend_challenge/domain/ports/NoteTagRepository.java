package com.lgnasolutions.backend_challenge.domain.ports;

import java.util.List;
import java.util.UUID;

public interface NoteTagRepository {
    List<UUID> getNoteIdsByTagId(UUID tagId);
    void assignTagToNote(UUID noteId, UUID tagId);
    void reassignNotesToTag(List<UUID> noteIds, UUID oldTadId, UUID newTagId);
    void removeTagFromNotes(List<UUID> noteIds, UUID tagId);
}
