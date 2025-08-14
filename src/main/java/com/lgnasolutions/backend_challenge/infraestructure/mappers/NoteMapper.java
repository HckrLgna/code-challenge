package com.lgnasolutions.backend_challenge.infraestructure.mappers;

import com.lgnasolutions.backend_challenge.domain.dto.NoteDTO;
import com.lgnasolutions.backend_challenge.domain.entities.Note;
import com.lgnasolutions.backend_challenge.infraestructure.datasource.NoteEntity;

public class NoteMapper {
    public static NoteDTO toDTO(Note note){
        return NoteDTO.builder()
                .id(note.getId())
                .userId(note.getUserId())
                .title(note.getTitle())
                .content(note.getContent())
                .isArchived(note.isArchived())
                .build();
    }
    public static NoteEntity toEntity(Note note){
        return NoteEntity.builder()
                .id(note.getId())
                .userId(note.getUserId())
                .title(note.getTitle())
                .content(note.getContent())
                .archived(note.isArchived())
                .build();
    }
    public static Note toEntity(NoteDTO dto){
        return Note.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .archived(dto.isArchived())
                .build();
    }
    public static Note toDomain(NoteEntity entity){
        return new Note(
                entity.getId(),
                entity.getUserId(),
                entity.getTitle(),
                entity.getContent(),
                entity.isArchived()
        );
    }
}
