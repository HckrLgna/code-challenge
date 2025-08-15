package com.lgnasolutions.backend_challenge.presentation.controllers;

import com.lgnasolutions.backend_challenge.application.services.NoteTagService;
import com.lgnasolutions.backend_challenge.application.services.TagService;
import com.lgnasolutions.backend_challenge.domain.dto.NoteDTO;
import com.lgnasolutions.backend_challenge.domain.dto.TagAssignNoteDTO;
import com.lgnasolutions.backend_challenge.domain.dto.TagDTO;
import com.lgnasolutions.backend_challenge.domain.dto.TagReassignDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;
    private final NoteTagService noteTagService;

    @PostMapping
    public ResponseEntity<TagDTO> create(@RequestBody TagDTO tagDTO, HttpServletRequest request) {
        UUID userId = (UUID) request.getAttribute("userId");

        TagDTO createdTag = tagService.create(userId, tagDTO);
        return ResponseEntity.ok(createdTag);
    }

    @GetMapping("/")
    public ResponseEntity<List<TagDTO>> list(HttpServletRequest request) {
        UUID userId = (UUID) request.getAttribute("userId");

        List<TagDTO> tags = tagService.getTags(userId);
        return ResponseEntity.ok(tags);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<TagDTO> delete(
            @PathVariable UUID id,
            HttpServletRequest request) {
        UUID userId = (UUID) request.getAttribute("userId");
        tagService.delete(userId, id);
        return ResponseEntity.noContent().build();
    }
    //TODO: List notes
    @GetMapping("/{tagId}/notes")
    public ResponseEntity<List<UUID>> notesByTagId(@PathVariable UUID tagId, HttpServletRequest request) {
        UUID userId = (UUID) request.getAttribute("userId");

        List<UUID> notes = noteTagService.getNotesByTag(userId, tagId);
        return ResponseEntity.ok(notes);
    }
    @PostMapping("/assign")
    public ResponseEntity<Void> assignTagToNote(
            @RequestBody TagAssignNoteDTO dto
            ){
        noteTagService.assignTagToNote(dto);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{tagId}/reassign-and-delete")
    public ResponseEntity<Void> reassignAndDelete(
            @PathVariable UUID tagId,
            @RequestBody TagReassignDTO dto,
            HttpServletRequest request
            ){
        UUID userId = (UUID) request.getAttribute("userId");
        noteTagService.reassignAndDeleteTag(userId, tagId, dto);
        return ResponseEntity.noContent().build();
    }
}
