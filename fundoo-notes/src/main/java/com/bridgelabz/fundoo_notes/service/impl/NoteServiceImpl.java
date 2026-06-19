package com.bridgelabz.fundoo_notes.service.impl;


import com.bridgelabz.fundoo_notes.dto.request.NoteRequest;
import com.bridgelabz.fundoo_notes.dto.response.NoteResponse;
import com.bridgelabz.fundoo_notes.entity.Note;
import com.bridgelabz.fundoo_notes.entity.User;
import com.bridgelabz.fundoo_notes.repository.LabelRepository;
import com.bridgelabz.fundoo_notes.repository.NoteRepository;
import com.bridgelabz.fundoo_notes.repository.UserRepository;
import com.bridgelabz.fundoo_notes.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import com.bridgelabz.fundoo_notes.entity.Label;
import com.bridgelabz.fundoo_notes.repository.LabelRepository;
import java.util.ArrayList;


import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final LabelRepository labelRepository;

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
    @Override
    public NoteResponse updateNote(
            Long noteId,
            NoteRequest request,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Note note = noteRepository.findById(noteId)
                .orElseThrow(() ->
                        new RuntimeException("Note not found"));

        if (!note.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }

        note.setTitle(request.getTitle());
        note.setDescription(request.getDescription());

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
    public String deleteNote(
            Long noteId,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Note note = noteRepository.findById(noteId)
                .orElseThrow(() ->
                        new RuntimeException("Note not found"));

        if (!note.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }

        noteRepository.delete(note);

        return "Note deleted successfully";
    }
    @Override
    public NoteResponse assignLabelToNote(
            Long noteId,
            Long labelId,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Note note = noteRepository.findById(noteId)
                .orElseThrow(() ->
                        new RuntimeException("Note not found"));

        Label label = labelRepository.findById(labelId)
                .orElseThrow(() ->
                        new RuntimeException("Label not found"));

        if (!note.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized note access");
        }

        if (!label.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized label access");
        }

        if (note.getLabels() == null) {
            note.setLabels(new ArrayList<>());
        }

        note.getLabels().add(label);

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
    public NoteResponse archiveNote(
            Long noteId,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Note note = noteRepository.findById(noteId)
                .orElseThrow(() ->
                        new RuntimeException("Note not found"));

        if (!note.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access");
        }

        note.setArchived(true);

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
}
