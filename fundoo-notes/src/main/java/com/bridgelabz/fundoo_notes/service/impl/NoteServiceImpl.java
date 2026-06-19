package com.bridgelabz.fundoo_notes.service.impl;


import com.bridgelabz.fundoo_notes.dto.request.NoteRequest;
import com.bridgelabz.fundoo_notes.dto.response.NoteResponse;
import com.bridgelabz.fundoo_notes.entity.Note;
import com.bridgelabz.fundoo_notes.entity.User;
import com.bridgelabz.fundoo_notes.repository.NoteRepository;
import com.bridgelabz.fundoo_notes.repository.UserRepository;
import com.bridgelabz.fundoo_notes.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    @Override
    public NoteResponse createNote(
            NoteRequest request,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Note note = Note.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .user(user)
                .build();

        noteRepository.save(note);

        return NoteResponse.builder()
                .id(note.getId())
                .title(note.getTitle())
                .description(note.getDescription())
                .pinned(note.isPinned())
                .archived(note.isArchived())
                .trashed(note.isTrashed())
                .build();
    }
    @Override
    public List<NoteResponse> getAllNotes(
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        List<Note> notes =
                noteRepository.findByUser(user);

        return notes.stream()
                .map(note -> NoteResponse.builder()
                        .id(note.getId())
                        .title(note.getTitle())
                        .description(note.getDescription())
                        .pinned(note.isPinned())
                        .archived(note.isArchived())
                        .trashed(note.isTrashed())
                        .build())
                .collect(Collectors.toList());
    }
}
