package it.nextdevs.GestioneEventi.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {

    @NotBlank(message = "Lo username dell'utente non può essere vuoto, mancante o composto da soli spazi")
    private String username;
    @Email
    @NotBlank(message = "L'email non può essere vuota, mancante o composta da soli spazi")
    private String email;
    @NotBlank(message = "La password non può essere vuota, mancante o composta da soli spazi")
    private String password;
}
