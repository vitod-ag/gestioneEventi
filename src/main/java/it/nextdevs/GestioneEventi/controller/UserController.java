package it.nextdevs.GestioneEventi.controller;

import it.nextdevs.GestioneEventi.DTO.UserDTO;
import it.nextdevs.GestioneEventi.entity.Event;
import it.nextdevs.GestioneEventi.entity.User;
import it.nextdevs.GestioneEventi.exception.BadRequestException;
import it.nextdevs.GestioneEventi.exception.UserNotFoundException;
import it.nextdevs.GestioneEventi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/api/users")
    @PreAuthorize("hasAnyAuthority('ORGANIZER', 'USER')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/api/users/{id}")
    @PreAuthorize("hasAnyAuthority('ORGANIZER', 'USER')")
    public User getUserById(@PathVariable int id) {
        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new UserNotFoundException("User with id:" + id + " not found");
        }
    }

    @PutMapping("/api/users/{id}")
    @PreAuthorize("hasAuthority('ORGANIZER')")
    public User updateUser(@PathVariable int id, @RequestBody @Validated UserDTO userDTO, BindingResult bindingResult) throws BadRequestException{
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .reduce("", (s1, s2) -> s1 + s2));
        }
        return userService.updateUser(id, userDTO);
    }

    @DeleteMapping("/api/users/{id}")
//    @PreAuthorize("hasAuthority('ORGANIZER')")
    public String deleteUser(@PathVariable int id) {
        System.out.println(id);
        return userService.deleteUser(id);
    }

    @GetMapping("/api/users/{id}/eventi_prenotati")
    @PreAuthorize("hasAuthority('USER')")
    public List<Event> getEventiPrenotati(@PathVariable int id) {
        return userService.getEventiPrenotati(id);
    }

    @DeleteMapping("/api/users/{utenteId}/eventi/{eventoId}/annulla_prenotazione")
    @PreAuthorize("hasAuthority('USER')")
    public String annullaPrenotazione(@PathVariable int utenteId, @PathVariable int eventoId) {
        return userService.annullaPrenotazione(utenteId, eventoId);
    }

}
