package com.bridgelabz.fundoo_notes.service;

import com.bridgelabz.fundoo_notes.dto.request.NoteRequest;
import com.bridgelabz.fundoo_notes.dto.response.NoteResponse;

import java.util.List;

public interface NoteService {

    NoteResponse createNote(
            NoteRequest request,
            String email
    );

    List<NoteResponse> getAllNotes(
            String email
    );

    NoteResponse updateNote(
            Long noteId,
            NoteRequest request,
            String email
    );

    String deleteNote(
            Long noteId,
            String email
    );

    NoteResponse assignLabelToNote(
            Long noteId,
            Long labelId,
            String email
    );
}