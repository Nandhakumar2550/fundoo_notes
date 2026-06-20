package com.bridgelabz.fundoo_notes.service;

import com.bridgelabz.fundoo_notes.dto.request.NoteRequest;
import com.bridgelabz.fundoo_notes.dto.response.NoteResponse;
import org.springframework.data.domain.Page;
import java.util.List;

public interface NoteService {

    NoteResponse createNote(NoteRequest request, String email);

    List<NoteResponse> getAllNotes(String email);

    NoteResponse updateNote(Long noteId, NoteRequest request, String email);

    String deleteNote(Long noteId, String email);

    NoteResponse assignLabelToNote(Long noteId, Long labelId, String email);

    NoteResponse archiveNote(Long noteId, String email);

    NoteResponse trashNote(Long noteId, String email);

    NoteResponse togglePinNote(Long noteId, String email);

    List<NoteResponse> searchNotes(String keyword, String email);

    List<NoteResponse> sortNotes(
            String by,
            String direction,
            String email
    );
    Page<NoteResponse> getPaginatedNotes(
            int page,
            int size,
            String email
    );
}