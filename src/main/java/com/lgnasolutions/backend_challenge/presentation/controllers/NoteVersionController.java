package com.lgnasolutions.backend_challenge.presentation.controllers;

import com.lgnasolutions.backend_challenge.application.services.NoteVersionService;
import com.lgnasolutions.backend_challenge.domain.dto.NoteVersionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notes/{noteId}/versions")
@RequiredArgsConstructor
public class NoteVersionController {
    private final NoteVersionService noteVersionService;
    @GetMapping
    public ResponseEntity<List<NoteVersionDTO>> getHistory(@PathVariable UUID noteId) {
        List<NoteVersionDTO> history = noteVersionService.getVersionHistory(noteId);
        return ResponseEntity.ok(history);
    }

    @PostMapping("/{versionId}/revert")
    public ResponseEntity<Void> revert(@PathVariable UUID noteId, @PathVariable UUID versionId) {
        noteVersionService.revertNoteVersion(noteId, versionId);
        return ResponseEntity.ok().build();
    }
}

