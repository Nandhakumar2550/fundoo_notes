package com.bridgelabz.fundoo_notes.service.impl;



import com.bridgelabz.fundoo_notes.dto.request.LabelRequest;
import com.bridgelabz.fundoo_notes.dto.response.LabelResponse;
import com.bridgelabz.fundoo_notes.entity.Label;
import com.bridgelabz.fundoo_notes.entity.User;
import com.bridgelabz.fundoo_notes.repository.LabelRepository;
import com.bridgelabz.fundoo_notes.repository.UserRepository;
import com.bridgelabz.fundoo_notes.service.LabelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LabelServiceImpl implements LabelService {

    private final LabelRepository labelRepository;
    private final UserRepository userRepository;

    @Override
    public LabelResponse createLabel(
            LabelRequest request,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Label label = Label.builder()
                .name(request.getName())
                .user(user)
                .build();

        labelRepository.save(label);

        return LabelResponse.builder()
                .id(label.getId())
                .name(label.getName())
                .build();
    }
}
