package it.nextdevs.GestioneEventi.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EventDTO {

    @NotBlank(message = "Il titolo non può essere vuoto o composto da soli spazi")
    private String titolo;

    @NotBlank(message = "La descrizione non può essere vuota o composta da soli spazi")
    private String descrizione;

    @NotBlank(message = "La data non può essere vuota o composta da soli spazi")
    private LocalDate data;

    @NotBlank(message = "Il luogo non può essere vuoto o composto da soli spazi")
    private String luogo;

    private int numeroPostiDisponibili;
}
