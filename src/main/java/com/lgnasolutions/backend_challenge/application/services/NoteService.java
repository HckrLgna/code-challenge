package com.lgnasolutions.backend_challenge.application.services;

import com.lgnasolutions.backend_challenge.domain.dto.NoteDTO;
import com.lgnasolutions.backend_challenge.domain.dto.NoteSearchCriteriaDTO;
import com.lgnasolutions.backend_challenge.domain.entities.Note;
import com.lgnasolutions.backend_challenge.domain.entities.NoteState;
import com.lgnasolutions.backend_challenge.domain.entities.NoteVersion;
import com.lgnasolutions.backend_challenge.domain.exceptions.CustomException;
import com.lgnasolutions.backend_challenge.domain.ports.NoteRepository;
import com.lgnasolutions.backend_challenge.domain.ports.NoteVersionRepository;
import com.lgnasolutions.backend_challenge.infraestructure.mappers.NoteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final NoteVersionRepository noteVersionRepository;

    public NoteDTO create(NoteDTO dto){
        Note note = Note.builder()
                .userId(dto.getUserId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .archived(dto.isArchived())
                .build();
        Note saved = noteRepository.create(note);
        return NoteMapper.toDTO(saved);
    }

    public NoteDTO getById(UUID id) {
        return noteRepository.findById(id)
                .map(NoteMapper::toDTO)
                .orElseThrow( () -> {
                    throw new CustomException("Note not found with id: " + id);
                });
    }
    public List<NoteDTO> getAll() {
        return noteRepository.findAll()
                .stream()
                .map(NoteMapper::toDTO)
                .collect(Collectors.toList());
    }
    public List<NoteDTO> getByCriteria(NoteSearchCriteriaDTO criteria){
        return noteRepository.findByCriteria(criteria)
                .stream()
                .map(NoteMapper::toDTO)
                .collect(Collectors.toList());
    }
    public NoteDTO update(NoteDTO dto) {
        NoteDTO currentNote =  this.getById(dto.getId());

        Note note = Note.builder()
                .id(dto.getId())
                .userId(dto.getUserId() != null ? dto.getUserId() : currentNote.getUserId())
                .title(dto.getTitle() != null ? dto.getTitle() : currentNote.getTitle())
                .content(dto.getContent() != null ? dto.getContent() : currentNote.getContent())
                .archived(dto.isArchived())
                .build();
        Note updated = noteRepository.update(note);
        return NoteMapper.toDTO(updated);
    }
    public void delete(UUID id) {
        noteRepository.delete(id);
    }
    public NoteDTO archive(UUID id){
        NoteDTO note = this.getById(id);
        note.setArchived(true);
        return update(note);
    }
    public NoteDTO unarchive(UUID id){
        NoteDTO note = this.getById(id);
        note.setArchived(false);
        return update(note);
    }
    public List<NoteDTO> exportNotes() {
        return noteRepository.findAll()
                .stream()
                .map(NoteMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void importNotes(UUID userId, List<NoteDTO> noteDTOs) {
        List<Note> notes = noteDTOs.stream()
                .map(dto -> Note.builder()
                        .title(dto.getTitle())
                        .content(dto.getContent())
                        .archived(dto.isArchived())
                        .userId(userId)
                        .build())
                .collect(Collectors.toList());
        noteRepository.saveAll(notes);
    }

    public NoteDTO editNote(UUID noteId, NoteDTO dto) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        NoteVersion prevVersion = createPreviousVersion(note);
        noteVersionRepository.save(prevVersion);

        updateNoteContent(note, dto);

        NoteVersion newState = createNewState(note, prevVersion.getVersionNumber() + 1);
        note.setCurrentState(newState);

        note.setVersions(noteVersionRepository.findByNoteId(note.getId()));

        Note updated = noteRepository.update(note);
        return NoteMapper.toDTO(updated);
    }

    private NoteVersion createPreviousVersion(Note note) {
        NoteState currentState = note.getCurrentState();
        if (currentState == null) {
            int lastVersionNumber = noteVersionRepository.findByNoteId(note.getId()).stream()
                    .mapToInt(NoteVersion::getVersionNumber)
                    .max()
                    .orElse(0);
            return NoteVersion.builder()
                    .noteId(note.getId())
                    .title(note.getTitle())
                    .content(note.getContent())
                    .versionNumber(lastVersionNumber + 1)
                    .createdAt(Instant.now())
                    .build();
        } else {
            return NoteVersion.builder()
                    .noteId(note.getId())
                    .title(currentState.getTitle())
                    .content(currentState.getContent())
                    .versionNumber(currentState.getVersionNumber())
                    .createdAt(currentState.getCreatedAt())
                    .build();
        }
    }

    private void updateNoteContent(Note note, NoteDTO dto) {
        note.setTitle(dto.getTitle() != null ? dto.getTitle() : note.getTitle());
        note.setContent(dto.getContent() != null ? dto.getContent() : note.getContent());
    }

    private NoteVersion createNewState(Note note, int versionNumber) {
        return NoteVersion.builder()
                .noteId(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .versionNumber(versionNumber)
                .createdAt(Instant.now())
                .build();
    }
}
