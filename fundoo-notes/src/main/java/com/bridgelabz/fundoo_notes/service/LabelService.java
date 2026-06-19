package com.bridgelabz.fundoo_notes.service;

import com.bridgelabz.fundoo_notes.dto.request.LabelRequest;
import com.bridgelabz.fundoo_notes.dto.response.LabelResponse;

import java.util.List;

public interface LabelService {

    LabelResponse createLabel(
            LabelRequest request,
            String email
    );

    List<LabelResponse> getAllLabels(
            String email
    );
}