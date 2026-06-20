package com.bridgelabz.fundoo_notes.repository;

import com.bridgelabz.fundoo_notes.entity.Note;
import com.bridgelabz.fundoo_notes.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoteRepository
        extends JpaRepository<Note, Long> {

    List<Note> findByUser(User user);

    @Query("""
            SELECT n FROM Note n
            WHERE n.user = :user
            AND (
                LOWER(n.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
                OR
                LOWER(n.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
            )
            """)
    List<Note> searchNotes(
            @Param("user") User user,
            @Param("keyword") String keyword
    );
    List<Note> findByUser(
            User user,
            Sort sort
    );

    Page<Note> findByUser(
            User user,
            Pageable pageable
    );
}