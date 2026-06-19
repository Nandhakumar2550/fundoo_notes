package com.bridgelabz.fundoo_notes.repository;

import com.bridgelabz.fundoo_notes.entity.Note;
import com.bridgelabz.fundoo_notes.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByUser(User user);

    List<Note> findByUserAndArchivedFalse(User user);

    List<Note> findByUserAndTrashedFalse(User user);
}