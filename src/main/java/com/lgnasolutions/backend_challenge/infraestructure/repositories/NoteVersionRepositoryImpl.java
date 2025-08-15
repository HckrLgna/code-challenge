package com.lgnasolutions.backend_challenge.infraestructure.repositories;

import com.lgnasolutions.backend_challenge.domain.entities.Note;
import com.lgnasolutions.backend_challenge.domain.entities.NoteVersion;
import com.lgnasolutions.backend_challenge.domain.ports.NoteVersionRepository;
import com.lgnasolutions.backend_challenge.infraestructure.mappers.NoteVersionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class NoteVersionRepositoryImpl implements NoteVersionRepository {
    private final NoteVersionJpaRepository noteVersionJpaRepository;
    @Autowired
    public NoteVersionRepositoryImpl(NoteVersionJpaRepository noteVersionJpaRepository) {
        this.noteVersionJpaRepository = noteVersionJpaRepository;
    }

    @Override
    public NoteVersion save(NoteVersion version) {
        return NoteVersionMapper.toDomain(
                noteVersionJpaRepository.save(NoteVersionMapper.toEntity(version))
        );
    }

    @Override
    public List<NoteVersion> findByNoteId(UUID noteId) {
        return noteVersionJpaRepository.findByNoteId(noteId)
                .stream()
                .map(NoteVersionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public NoteVersion findById(UUID id) {
        return noteVersionJpaRepository.findById(id)
                .map(NoteVersionMapper::toDomain)
                .orElseThrow(() -> new RuntimeException("Version not found"));
    }
}
