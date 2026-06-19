package com.bridgelabz.fundoo_notes.service;

import com.bridgelabz.fundoo_notes.dto.request.NoteRequest;
import com.bridgelabz.fundoo_notes.dto.response.NoteResponse;

public interface NoteService {

    NoteResponse createNote(
            NoteRequest request,
            String email
    );
}
