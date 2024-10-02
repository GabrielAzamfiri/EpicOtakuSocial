package com.example.EpicOtakuSocial.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CommentoDTO(
        @NotEmpty(message = "Campo obbligatorio. Inserire commento.")
        @Size( max = 200, message = "Il commento può avere max 200 caratteri")
        String commento,
        @NotEmpty(message = "Campo obbligatorio. Inserire l'elemento da commentare.")
        String elementoCommentato
) {
}
