package com.lgnasolutions.backend_challenge.infraestructure.repositories;

import com.lgnasolutions.backend_challenge.domain.ports.NoteTagRepository;
import com.lgnasolutions.backend_challenge.infraestructure.datasource.NoteTagEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class NoteTagRepositoryImpl implements NoteTagRepository {
    private final NoteTagJpaRepository noteTagJpaRepository;

    @Autowired
    public NoteTagRepositoryImpl(NoteTagJpaRepository noteTagJpaRepository) {
        this.noteTagJpaRepository = noteTagJpaRepository;
    }

    @Override
    public List<UUID> getNoteIdsByTagId(UUID tagId) {
        return noteTagJpaRepository.findByTagId(tagId)
                .stream()
                .map(NoteTagEntity::getNoteId)
                .collect(Collectors.toList());
    }

    @Override
    public void assignTagToNote(UUID noteId, UUID tagId) {
        noteTagJpaRepository.save(new NoteTagEntity(noteId, tagId));
    }

    @Override
    public void reassignNotesToTag(List<UUID> noteIds, UUID oldTadId, UUID newTagId) {
        for( UUID noteId : noteIds ){
            //Removing the old tag association
            NoteTagEntity.NoteTagId oldId = new NoteTagEntity.NoteTagId();
            oldId.setNoteId(noteId);
            oldId.setTagId(oldTadId);
            noteTagJpaRepository.deleteById(oldId);
            //Adding the new tag association
            NoteTagEntity newEntity = NoteTagEntity
                    .builder()
                    .noteId(noteId)
                    .tagId(newTagId)
                    .build();
            noteTagJpaRepository.save(newEntity);
        }
    }

    @Override
    public void removeTagFromNotes(List<UUID> noteIds, UUID tagId) {
        for ( UUID noteId : noteIds ){
            NoteTagEntity.NoteTagId oldId = new NoteTagEntity.NoteTagId();
            oldId.setNoteId(noteId);
            oldId.setTagId(tagId);
            noteTagJpaRepository.deleteById(oldId);
        }
    }
}
