package com.bridgelabz.fundoo_notes.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LabelRequest {

    @NotBlank(message = "Label name is required")
    private String name;
}
