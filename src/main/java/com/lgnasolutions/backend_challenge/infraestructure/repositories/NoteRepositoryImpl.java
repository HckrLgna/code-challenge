package com.lgnasolutions.backend_challenge.infraestructure.repositories;

import com.lgnasolutions.backend_challenge.domain.dto.NoteSearchCriteriaDTO;
import com.lgnasolutions.backend_challenge.domain.entities.Note;
import com.lgnasolutions.backend_challenge.domain.ports.NoteRepository;
import com.lgnasolutions.backend_challenge.infraestructure.datasource.NoteEntity;
import com.lgnasolutions.backend_challenge.infraestructure.mappers.NoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class NoteRepositoryImpl implements NoteRepository {
    private final NoteJpaRepository noteJpaRepository;

    @Autowired
    public NoteRepositoryImpl(NoteJpaRepository noteJpaRepository) {
        this.noteJpaRepository = noteJpaRepository;
    }


    @Override
    public List<Note> findAll() {
        return noteJpaRepository.findAll()
                .stream()
                .map(NoteMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Note create(Note note) {
        NoteEntity entity = NoteMapper.toEntity(note);
        NoteEntity savedEntity = noteJpaRepository.save(entity);
        return NoteMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Note> findById(UUID id) {
        return noteJpaRepository.findById(id).map(NoteMapper::toDomain);
    }

    @Override
    public List<Note> findByCriteria(NoteSearchCriteriaDTO criteria) {
        List<NoteEntity> entities = fetchNoteEntities(criteria);

        return entities.stream()
                .map(NoteMapper::toDomain)
                .collect(Collectors.toList());

    }

    private List<NoteEntity> fetchNoteEntities(NoteSearchCriteriaDTO criteria) {
        UUID userId = criteria.getUserId();
        Boolean isArchived = criteria.getIsArchived();
        String title = criteria.getTitle();


        if (isArchived != null && title != null) {
            return noteJpaRepository.findByUserIdAndArchivedAndTitleContainingIgnoreCase(userId, isArchived, title);
        }
        if (isArchived != null) {
            return noteJpaRepository.findByUserIdAndArchived(userId, isArchived);
        }
        if (title != null) {
            return noteJpaRepository.findByUserIdAndTitleContainingIgnoreCase(userId, title);
        }
        return noteJpaRepository.findByUserId(userId);
    }
    @Override
    public Note update(Note note) {
        NoteEntity entity = NoteMapper.toEntity(note);
        NoteEntity updatedEntity = noteJpaRepository.save(entity);
        return NoteMapper.toDomain(updatedEntity);
    }

    @Override
    public void delete(UUID id) {
        noteJpaRepository.deleteById(id);
    }

    @Override
    public void saveAll(List<Note> notes) {
        List<NoteEntity> entities = notes.stream()
                .map(NoteMapper::toEntity)
                .collect(Collectors.toList());
        noteJpaRepository.saveAll(entities);
    }
}
