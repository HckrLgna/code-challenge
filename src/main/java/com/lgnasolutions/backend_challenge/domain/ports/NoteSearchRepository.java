package com.lgnasolutions.backend_challenge.domain.ports;

import com.lgnasolutions.backend_challenge.domain.dto.NoteSearchCriteriaDTO;
import com.lgnasolutions.backend_challenge.domain.entities.Note;

import java.util.List;

public interface NoteSearchRepository {
    List<Note> searchNotes(NoteSearchCriteriaDTO criteria);
}
