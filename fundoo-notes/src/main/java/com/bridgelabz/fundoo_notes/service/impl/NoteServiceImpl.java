package com.bridgelabz.fundoo_notes.service.impl;

import com.bridgelabz.fundoo_notes.dto.request.NoteRequest;
import com.bridgelabz.fundoo_notes.dto.response.NoteResponse;
import com.bridgelabz.fundoo_notes.entity.Label;
import com.bridgelabz.fundoo_notes.entity.Note;
import com.bridgelabz.fundoo_notes.entity.User;
import com.bridgelabz.fundoo_notes.exception.ResourceNotFoundException;
import com.bridgelabz.fundoo_notes.exception.UnauthorizedException;
import com.bridgelabz.fundoo_notes.repository.LabelRepository;
import com.bridgelabz.fundoo_notes.repository.NoteRepository;
import com.bridgelabz.fundoo_notes.repository.UserRepository;
import com.bridgelabz.fundoo_notes.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private static final Logger logger =
            LoggerFactory.getLogger(NoteServiceImpl.class);

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final LabelRepository labelRepository;

    @Override
    public NoteResponse createNote(NoteRequest request, String email) {

        logger.info("Creating note for user: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Note note = Note.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .user(user)
                .build();

        noteRepository.save(note);

        logger.info("Note created successfully ID: {}", note.getId());

        return mapToResponse(note);
    }

    @Override
    public List<NoteResponse> getAllNotes(String email) {

        logger.info("Fetching all notes for user: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return noteRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public NoteResponse updateNote(Long noteId, NoteRequest request, String email) {

        logger.info("Updating note ID: {}", noteId);

        Note note = getAuthorizedNote(noteId, email);

        note.setTitle(request.getTitle());
        note.setDescription(request.getDescription());

        noteRepository.save(note);

        logger.info("Note updated successfully ID: {}", noteId);

        return mapToResponse(note);
    }

    @Override
    public String deleteNote(Long noteId, String email) {

        logger.info("Deleting note ID: {}", noteId);

        Note note = getAuthorizedNote(noteId, email);

        noteRepository.delete(note);

        logger.info("Note deleted successfully ID: {}", noteId);

        return "Note deleted successfully";
    }

    @Override
    public NoteResponse assignLabelToNote(Long noteId, Long labelId, String email) {

        logger.info("Assigning label {} to note {}", labelId, noteId);

        Note note = getAuthorizedNote(noteId, email);

        Label label = labelRepository.findById(labelId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Label not found"));

        if (note.getLabels() == null) {
            note.setLabels(new ArrayList<>());
        }

        note.getLabels().add(label);

        noteRepository.save(note);

        return mapToResponse(note);
    }

    @Override
    public NoteResponse archiveNote(Long noteId, String email) {

        logger.info("Archiving note ID: {}", noteId);

        Note note = getAuthorizedNote(noteId, email);

        note.setArchived(true);

        noteRepository.save(note);

        return mapToResponse(note);
    }

    @Override
    public NoteResponse trashNote(Long noteId, String email) {

        logger.info("Trashing note ID: {}", noteId);

        Note note = getAuthorizedNote(noteId, email);

        note.setTrashed(true);

        noteRepository.save(note);

        return mapToResponse(note);
    }

    @Override
    public NoteResponse togglePinNote(Long noteId, String email) {

        logger.info("Toggling pin for note ID: {}", noteId);

        Note note = getAuthorizedNote(noteId, email);

        note.setPinned(!note.isPinned());

        noteRepository.save(note);

        return mapToResponse(note);
    }

    private Note getAuthorizedNote(Long noteId, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Note note = noteRepository.findById(noteId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Note not found"));

        if (!note.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("Unauthorized access");
        }

        return note;
    }

    private NoteResponse mapToResponse(Note note) {
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
    public List<NoteResponse> searchNotes(
            String keyword,
            String email) {

        logger.info("Searching notes for user: {} with keyword: {}", email, keyword);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        List<Note> notes =
                noteRepository.searchNotes(user, keyword);

        return notes.stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    public List<NoteResponse> sortNotes(
            String by,
            String direction,
            String email) {

        logger.info(
                "Sorting notes for user: {} by {} {}",
                email,
                by,
                direction
        );

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(by).descending()
                : Sort.by(by).ascending();

        List<Note> notes =
                noteRepository.findByUser(user, sort);

        return notes.stream()
                .map(this::mapToResponse)
                .toList();
    }
    @Override
    public Page<NoteResponse> getPaginatedNotes(
            int page,
            int size,
            String email) {

        logger.info(
                "Fetching paginated notes for user: {}, page: {}, size: {}",
                email,
                page,
                size
        );

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Pageable pageable =
                PageRequest.of(page, size);

        Page<Note> notes =
                noteRepository.findByUser(
                        user,
                        pageable
                );

        return notes.map(this::mapToResponse);
    }
}