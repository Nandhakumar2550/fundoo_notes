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
    }@DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNote(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                noteService.deleteNote(id, email)
        );
    }
    @PutMapping("/{noteId}/labels/{labelId}")
    public ResponseEntity<NoteResponse> assignLabelToNote(
            @PathVariable Long noteId,
            @PathVariable Long labelId,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                noteService.assignLabelToNote(
                        noteId,
                        labelId,
                        email
                )
        );
    }
    @PutMapping("/{id}/archive")
    public ResponseEntity<NoteResponse> archiveNote(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                noteService.archiveNote(id, email)
        );
    }
    @PutMapping("/{id}/trash")
    public ResponseEntity<NoteResponse> trashNote(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                noteService.trashNote(id, email)
        );
    }@PutMapping("/{id}/pin")
    public ResponseEntity<NoteResponse> togglePinNote(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                noteService.togglePinNote(id, email)
        );
    }
    @GetMapping("/search")
    public ResponseEntity<List<NoteResponse>> searchNotes(
            @RequestParam String keyword,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                noteService.searchNotes(
                        keyword,
                        email
                )
        );
    }
    @GetMapping("/sort")
    public ResponseEntity<List<NoteResponse>> sortNotes(
            @RequestParam String by,
            @RequestParam String direction,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                noteService.sortNotes(
                        by,
                        direction,
                        email
                )
        );
    }
}
