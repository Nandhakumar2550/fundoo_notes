package com.bridgelabz.fundoo_notes.repository;


import com.bridgelabz.fundoo_notes.entity.Label;
import com.bridgelabz.fundoo_notes.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabelRepository extends JpaRepository<Label, Long> {

    List<Label> findByUser(User user);
}