package com.lgnasolutions.backend_challenge.application.services;

import com.lgnasolutions.backend_challenge.domain.dto.NoteSearchCriteriaDTO;
import com.lgnasolutions.backend_challenge.domain.entities.Note;
import com.lgnasolutions.backend_challenge.domain.ports.NoteSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoteSearchService {
    private final NoteSearchRepository noteSearchRepository;

    public List<Note> searchNotes( NoteSearchCriteriaDTO criteria ) {
        return noteSearchRepository.searchNotes( criteria );
    }
}
