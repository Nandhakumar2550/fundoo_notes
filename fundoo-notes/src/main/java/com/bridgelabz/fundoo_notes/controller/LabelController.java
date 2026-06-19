package com.bridgelabz.fundoo_notes.controller;



import com.bridgelabz.fundoo_notes.dto.request.LabelRequest;
import com.bridgelabz.fundoo_notes.dto.response.LabelResponse;
import com.bridgelabz.fundoo_notes.service.LabelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/labels")
@RequiredArgsConstructor
public class LabelController {

    private final LabelService labelService;

    @PostMapping
    public ResponseEntity<LabelResponse> createLabel(
            @Valid @RequestBody LabelRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                labelService.createLabel(request, email)
        );
    }
}
