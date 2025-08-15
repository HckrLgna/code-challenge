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

    public void revertNoteVersion(UUID versionId) {
        NoteVersion version = noteVersionRepository.findById(versionId);
        if (version == null) {
            throw new RuntimeException("NoteVersion not found");
        }
        Note note = noteRepository.findById(version.getNoteId())
                .orElseThrow(() -> new RuntimeException("Note not found"));

        note.setTitle(version.getTitle());
        note.setContent(version.getContent());
        note.setCurrentState(version);
        noteRepository.update(note);
    }
}
