package com.lgnasolutions.backend_challenge.presentation.controllers;

import com.lgnasolutions.backend_challenge.application.services.FilterStateService;
import com.lgnasolutions.backend_challenge.domain.dto.FilterStateDTO;
import com.lgnasolutions.backend_challenge.domain.entities.FilterState;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/filter-state")
@RequiredArgsConstructor
public class FilterStateController {
    private final FilterStateService filterStateService;
    @PostMapping
    public ResponseEntity<FilterStateDTO> save(@RequestBody FilterStateDTO dto, HttpServletRequest request) {
        UUID userId = (UUID) request.getAttribute("userId");
        FilterStateDTO savedDto = filterStateService.saveState(userId, dto);
        return ResponseEntity.ok(savedDto);
    }
    @GetMapping("/")
    public ResponseEntity<List<FilterStateDTO>> getAll(HttpServletRequest request) {
        UUID userId = (UUID) request.getAttribute("userId");
        List<FilterStateDTO> states = filterStateService.getFiltersState(userId);
        return ResponseEntity.ok(states);
    }
}
