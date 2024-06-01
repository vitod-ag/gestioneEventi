package it.nextdevs.GestioneEventi.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginDTO {
    @Email
    @NotBlank(message = "L'email non può essere vuota, mancante o composta da soli spazi")
    private String email;
    @NotBlank(message = "La password non può essere vuota, mancante o composta da soli spazi")
    private String password;
}
