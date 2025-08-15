package com.lgnasolutions.backend_challenge.application.services;

import com.lgnasolutions.backend_challenge.domain.dto.NoteVersionDTO;
import com.lgnasolutions.backend_challenge.domain.entities.Note;
import com.lgnasolutions.backend_challenge.domain.entities.NoteVersion;
import com.lgnasolutions.backend_challenge.domain.ports.NoteRepository;
import com.lgnasolutions.backend_challenge.domain.ports.NoteVersionRepository;
import com.lgnasolutions.backend_challenge.infraestructure.mappers.NoteVersionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteVersionService {
    private final NoteVersionRepository noteVersionRepository;
    private final NoteRepository noteRepository;

    public List<NoteVersionDTO> getVersionHistory(UUID noteId) {
        return noteVersionRepository.findByNoteId(noteId)
                .stream()
                .map(NoteVersionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void revertNoteVersion(UUID noteId, UUID versionId) {
        NoteVersion version = noteVersionRepository.findById(versionId);
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));
        note.setCurrentState(version);
        noteRepository.create(note);
    }
}
