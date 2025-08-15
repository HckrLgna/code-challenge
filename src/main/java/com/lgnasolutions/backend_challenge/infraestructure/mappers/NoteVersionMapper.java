package com.lgnasolutions.backend_challenge.infraestructure.mappers;

import com.lgnasolutions.backend_challenge.domain.dto.NoteVersionDTO;
import com.lgnasolutions.backend_challenge.domain.entities.NoteVersion;
import com.lgnasolutions.backend_challenge.infraestructure.datasource.NoteVersionEntity;

public class NoteVersionMapper {
    public static NoteVersionDTO toDTO(NoteVersion version) {
        return NoteVersionDTO.builder()
                .id(version.getId())
                .noteId(version.getNoteId())
                .title(version.getTitle())
                .content(version.getContent())
                .versionNumber(version.getVersionNumber())
                .createdAt(version.getCreatedAt())
                .build();
    }
    public static NoteVersion toDomain(NoteVersionEntity entity) {
        return NoteVersion.builder()
                .id(entity.getId())
                .noteId(entity.getNoteId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .versionNumber(entity.getVersionNumber())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public static NoteVersionEntity toEntity(NoteVersion version) {
        return NoteVersionEntity.builder()
                .id(version.getId())
                .noteId(version.getNoteId())
                .title(version.getTitle())
                .content(version.getContent())
                .versionNumber(version.getVersionNumber())
                .createdAt(version.getCreatedAt())
                .build();
    }
}
