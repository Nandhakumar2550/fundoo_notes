package com.bridgelabz.fundoo_notes.controller;

import com.bridgelabz.fundoo_notes.dto.request.NoteRequest;
import com.bridgelabz.fundoo_notes.dto.response.NoteResponse;
import com.bridgelabz.fundoo_notes.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteResponse> createNote(
            @Valid @RequestBody NoteRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                noteService.createNote(request, email)
        );
    }
    @GetMapping
    public ResponseEntity<List<NoteResponse>> getAllNotes(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                noteService.getAllNotes(email)
        );
    }
    @PutMapping("/{id}")
    public ResponseEntity<NoteResponse> updateNote(
            @PathVariable Long id,
            @Valid @RequestBody NoteRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                noteService.updateNote(id, request, email)
        );
    }
}
