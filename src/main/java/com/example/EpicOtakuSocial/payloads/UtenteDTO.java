package com.example.EpicOtakuSocial.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UtenteDTO(
        @NotEmpty(message = "Campo obbligatorio. Inserire username.")
        @Size(min = 3, max = 30, message = "L'username deve essere compreso tra 3 e 30 caratteri")
        String username,
        @NotEmpty(message = "Campo obbligatorio. Inserire email.")
        @Size(min = 3, max = 30, message = "L'email deve essere compreso tra 3 e 30 caratteri")
        @Email(message = "Inserire una email corretta.")
        String email,
        @NotEmpty(message = "Campo obbligatorio. Inserire password.")
        @Size(min = 3, max = 30, message = "La password deve essere compreso tra 3 e 30 caratteri")
        String password,
        @NotEmpty(message = "Campo obbligatorio. Inserire nome.")
        @Size(min = 3, max = 30, message = "Il nome deve essere compreso tra 3 e 30 caratteri")
        String nome,
        @NotEmpty(message = "Campo obbligatorio. Inserire cognome.")
        @Size(min = 3, max = 30, message = "Il cognome deve essere compreso tra 3 e 30 caratteri")
        String cognome
) {
}
