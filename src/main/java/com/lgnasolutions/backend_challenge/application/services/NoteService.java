package com.lgnasolutions.backend_challenge.application.services;

import com.lgnasolutions.backend_challenge.domain.dto.NoteDTO;
import com.lgnasolutions.backend_challenge.domain.dto.NoteSearchCriteriaDTO;
import com.lgnasolutions.backend_challenge.domain.entities.Note;
import com.lgnasolutions.backend_challenge.domain.exceptions.CustomException;
import com.lgnasolutions.backend_challenge.domain.ports.NoteRepository;
import com.lgnasolutions.backend_challenge.infraestructure.mappers.NoteMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;

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

    public List<NoteDTO> getByCriteria(NoteSearchCriteriaDTO criteria){
        return noteRepository.findByCriteria(criteria)
                .stream()
                .map(NoteMapper::toDTO)
                .collect(Collectors.toList());
    }
    public NoteDTO update(NoteDTO dto) {
        NoteDTO currentNote =  getById(dto.getId());

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

}
