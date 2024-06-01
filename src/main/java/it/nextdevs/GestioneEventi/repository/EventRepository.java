package it.nextdevs.GestioneEventi.repository;

import it.nextdevs.GestioneEventi.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {}
