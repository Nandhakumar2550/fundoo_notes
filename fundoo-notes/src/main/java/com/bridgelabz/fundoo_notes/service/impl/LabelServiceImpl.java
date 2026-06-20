package com.bridgelabz.fundoo_notes.service.impl;

import com.bridgelabz.fundoo_notes.dto.request.LabelRequest;
import com.bridgelabz.fundoo_notes.dto.response.LabelResponse;
import com.bridgelabz.fundoo_notes.entity.Label;
import com.bridgelabz.fundoo_notes.entity.User;
import com.bridgelabz.fundoo_notes.exception.ResourceNotFoundException;
import com.bridgelabz.fundoo_notes.repository.LabelRepository;
import com.bridgelabz.fundoo_notes.repository.UserRepository;
import com.bridgelabz.fundoo_notes.service.LabelService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LabelServiceImpl implements LabelService {

    private static final Logger logger =
            LoggerFactory.getLogger(LabelServiceImpl.class);

    private final LabelRepository labelRepository;
    private final UserRepository userRepository;

    @Override
    public LabelResponse createLabel(LabelRequest request, String email) {

        logger.info("Creating label for user: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Label label = Label.builder()
                .name(request.getName())
                .user(user)
                .build();

        labelRepository.save(label);

        logger.info("Label created successfully: {}", label.getName());

        return LabelResponse.builder()
                .id(label.getId())
                .name(label.getName())
                .build();
    }

    @Override
    public List<LabelResponse> getAllLabels(String email) {

        logger.info("Fetching all labels for user: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return labelRepository.findByUser(user)
                .stream()
                .map(label -> LabelResponse.builder()
                        .id(label.getId())
                        .name(label.getName())
                        .build())
                .collect(Collectors.toList());
    }
}