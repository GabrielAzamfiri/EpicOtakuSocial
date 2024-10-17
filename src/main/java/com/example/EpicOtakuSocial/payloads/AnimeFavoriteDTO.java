package com.example.EpicOtakuSocial.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AnimeFavoriteDTO(
        @NotNull(message = "Campo obbligatorio. Inserire id.")
        Long id,
        @NotEmpty(message = "Campo obbligatorio. Inserire title.")
        String title,
        @NotEmpty(message = "Campo obbligatorio. Inserire image.")
        String image,
        @NotEmpty(message = "Campo obbligatorio. Inserire genres.")
        List<String> genres,
        @NotEmpty(message = "Campo obbligatorio. Inserire aired.")
        String aired,
        @NotEmpty(message = "Campo obbligatorio. Inserire synopsis.")
        String synopsis
) {
}
