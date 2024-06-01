package it.nextdevs.GestioneEventi.controller;

import it.nextdevs.GestioneEventi.DTO.EventDTO;
import it.nextdevs.GestioneEventi.entity.Event;
import it.nextdevs.GestioneEventi.exception.BadRequestException;
import it.nextdevs.GestioneEventi.exception.EventNotFoundException;
import it.nextdevs.GestioneEventi.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("/api/eventi")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public String createEvento(@RequestBody @Validated EventDTO eventDTO, BindingResult bindingResult) throws BadRequestException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).reduce("", (s, s2) -> s + s2));
        }
        return eventService.saveEvento(eventDTO);
    }

    @GetMapping("/api/eventi")
    public List<Event> getAllEventi() {
        return eventService.getAllEventi();
    }

    @GetMapping("/api/eventi/{id}")
    public Event getEventoById(@PathVariable int id) {
        Optional<Event> eventoOptional = eventService.getEventoById(id);

        if (eventoOptional.isPresent()) {
            return eventoOptional.get();
        } else {
            throw new EventNotFoundException("Evento with id=" + id + " not found");
        }
    }

    @PutMapping("/api/eventi/{id}")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public String updateEvento(@PathVariable int id, @RequestBody @Validated EventDTO eventDTO, BindingResult bindingResult) throws BadRequestException {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).reduce("", (s, s2) -> s + s2));
        }
        return eventService.updateEvento(id, eventDTO);
    }

    @DeleteMapping("/api/eventi/{id}")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public String deleteEvento(@PathVariable int id) {
        return eventService.deleteEvento(id);
    }

    @PostMapping("/api/eventi/{id}/prenotazioni")
    @PreAuthorize("hasAuthority('USER')")
    public String prenotaPosto(@PathVariable int id) {
        return eventService.prenotaPosto(id);
    }
}
