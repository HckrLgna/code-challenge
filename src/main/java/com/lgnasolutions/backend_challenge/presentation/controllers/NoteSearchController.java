package com.lgnasolutions.backend_challenge.presentation.controllers;

import com.lgnasolutions.backend_challenge.application.services.NoteSearchService;
import com.lgnasolutions.backend_challenge.domain.dto.NoteSearchCriteriaDTO;
import com.lgnasolutions.backend_challenge.domain.entities.Note;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notes/search")
@RequiredArgsConstructor
public class NoteSearchController {
    private final NoteSearchService noteSearchService;
    @PostMapping
    public ResponseEntity<List<Note>> searchNotes(
            @RequestBody NoteSearchCriteriaDTO criteriaDTO
    ){
        List<Note> notes = noteSearchService.searchNotes(criteriaDTO);
        return ResponseEntity.ok(notes);
    }
}
