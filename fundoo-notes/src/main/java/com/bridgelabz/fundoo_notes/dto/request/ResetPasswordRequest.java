package com.bridgelabz.fundoo_notes.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordRequest {

    private String token;
    private String newPassword;
}