package com.lgnasolutions.backend_challenge.application.services;

import com.lgnasolutions.backend_challenge.domain.dto.TagAssignNoteDTO;
import com.lgnasolutions.backend_challenge.domain.dto.TagReassignDTO;
import com.lgnasolutions.backend_challenge.domain.exceptions.CustomException;
import com.lgnasolutions.backend_challenge.domain.ports.NoteRepository;
import com.lgnasolutions.backend_challenge.domain.ports.NoteTagRepository;
import com.lgnasolutions.backend_challenge.domain.ports.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoteTagService {
    private final NoteTagRepository noteTagRepository;
    private final TagRepository tagRepository;
    private final NoteRepository noteRepository;

    public List<UUID> getNotesByTag(UUID userId, UUID tagId){
        tagRepository.findById(tagId)
                .orElseThrow( ()-> new CustomException("Tag not found"));
        return noteTagRepository.getNoteIdsByTagId(tagId);
    }
    public void assignTagToNote(TagAssignNoteDTO tagAssignNoteDTO) {
        UUID tagId = tagAssignNoteDTO.getTagId();
        UUID noteId = tagAssignNoteDTO.getNoteId();
        tagRepository.findById(tagId)
                .orElseThrow( ()-> new CustomException("Tag not found"));
        noteRepository.findById(noteId)
                .orElseThrow( ()-> new CustomException("Note not found"));

        noteTagRepository.assignTagToNote(noteId, tagId);
    }

    public void reassignAndDeleteTag(UUID userId, UUID oldTagId, TagReassignDTO tagReassignDTO) {
        tagRepository.findById(tagReassignDTO.getNewTagId())
                .orElseThrow( ()-> new CustomException("New Tag not found"));
        tagRepository.findById(oldTagId)
                .orElseThrow( ()-> new CustomException("Old Tag not found"));
        noteTagRepository.reassignNotesToTag(tagReassignDTO.getNoteIds(), oldTagId, tagReassignDTO.getNewTagId());
        tagRepository.delete(oldTagId);
    }
}
