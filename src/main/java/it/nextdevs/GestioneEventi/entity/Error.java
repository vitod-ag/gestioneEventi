package it.nextdevs.GestioneEventi.entity;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@Data
public class Error {
    private String messaggio;
    private LocalDateTime dataErrore;
    private HttpStatus statoErrore;
}
