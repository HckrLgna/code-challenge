package com.lgnasolutions.backend_challenge.presentation.controllers;

import com.lgnasolutions.backend_challenge.application.services.NoteService;
import com.lgnasolutions.backend_challenge.domain.dto.NoteDTO;
import com.lgnasolutions.backend_challenge.domain.dto.NoteSearchCriteriaDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteDTO> create(@Valid @RequestBody NoteDTO noteDTO, HttpServletRequest request){
        UUID uuid = (UUID) request.getAttribute("userId");
        noteDTO.setUserId(uuid);

        NoteDTO created = noteService.create(noteDTO);
        return ResponseEntity.ok(created);
    }
    @GetMapping("/")
    public ResponseEntity<List<NoteDTO>> getAll(
            @RequestParam(required = false) Boolean isArchived,
            @RequestParam(required = false) String title
    ) {

        UUID userId = (UUID) RequestContextHolder.currentRequestAttributes().getAttribute("userId", RequestAttributes.SCOPE_REQUEST);

        NoteSearchCriteriaDTO searchCriteriaDTO = NoteSearchCriteriaDTO.builder()
                .userId(userId)
                .title(title)
                .isArchived(isArchived)
                .build();

        List<NoteDTO> notes = noteService.getByCriteria(searchCriteriaDTO);
        return ResponseEntity.ok(notes);
    }
    @PutMapping("/{id}")
    public ResponseEntity<NoteDTO> update(@PathVariable UUID id, @RequestBody NoteDTO noteDTO) {
        noteDTO.setId(id);
        NoteDTO updated = noteService.update(noteDTO);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        noteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
