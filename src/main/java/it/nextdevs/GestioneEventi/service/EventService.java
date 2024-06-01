package it.nextdevs.GestioneEventi.service;

import it.nextdevs.GestioneEventi.DTO.EventDTO;
import it.nextdevs.GestioneEventi.entity.Event;
import it.nextdevs.GestioneEventi.exception.EventNotFoundException;
import it.nextdevs.GestioneEventi.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public String saveEvento(EventDTO eventDTO) {
        Event event = new Event();
        event.setTitolo(eventDTO.getTitolo());
        event.setDescrizione(eventDTO.getDescrizione());
        event.setData(eventDTO.getData());
        event.setLuogo(eventDTO.getLuogo());
        event.setNumeroPostiDisponibili(eventDTO.getNumeroPostiDisponibili());

        eventRepository.save(event);
        return "Evento with id = " + event.getId() + " correctly saved";
    }

    public List<Event> getAllEventi() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventoById(int id) {
        return eventRepository.findById(id);
    }

    public String updateEvento(int id, EventDTO eventDTO) {
        Optional<Event> eventoOptional = getEventoById(id);

        if (eventoOptional.isPresent()) {
            Event evento = eventoOptional.get();
            evento.setTitolo(eventDTO.getTitolo());
            evento.setDescrizione(eventDTO.getDescrizione());
            evento.setData(eventDTO.getData());
            evento.setLuogo(eventDTO.getLuogo());
            evento.setNumeroPostiDisponibili(eventDTO.getNumeroPostiDisponibili());

            eventRepository.save(evento);
            return "Evento with id=" + id + " updated successfully";
        } else {
            throw new EventNotFoundException("Evento with id=" + id + " not found");
        }
    }

    public String deleteEvento(int id) {
        Optional<Event> eventoOptional = getEventoById(id);

        if (eventoOptional.isPresent()) {
            eventRepository.deleteById(id);
            return "Evento with id=" + id + " deleted successfully";
        } else {
            throw new EventNotFoundException("Evento with id=" + id + " not found");
        }
    }

    public String prenotaPosto(int id) {
        Optional<Event> eventoOptional = getEventoById(id);

        if (eventoOptional.isPresent()) {
            Event evento = eventoOptional.get();
            if (evento.getNumeroPostiDisponibili() > 0) {
                evento.setNumeroPostiDisponibili(evento.getNumeroPostiDisponibili() - 1);
                eventRepository.save(evento);
                return "Posto prenotato con successo per l'evento con id=" + id;
            } else {
                throw new EventNotFoundException("Non ci sono posti disponibili per l'evento con id=" + id);
            }
        }
        else {
            throw new EventNotFoundException("Evento with id=" + id + " not found");
        }
    }
}
