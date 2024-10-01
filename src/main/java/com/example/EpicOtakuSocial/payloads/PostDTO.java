package com.example.EpicOtakuSocial.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record PostDTO(
        @NotEmpty(message = "Campo obbligatorio. Inserire text.")
        @Size( max = 200, message = "Il text può avere max 200 caratteri")
        String text
) {
}
