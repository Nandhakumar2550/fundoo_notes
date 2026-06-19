package com.bridgelabz.fundoo_notes.service;


import com.bridgelabz.fundoo_notes.dto.request.LabelRequest;
import com.bridgelabz.fundoo_notes.dto.response.LabelResponse;

public interface LabelService {

    LabelResponse createLabel(
            LabelRequest request,
            String email
    );
}
