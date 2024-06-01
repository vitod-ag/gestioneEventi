package it.nextdevs.GestioneEventi.service;

import it.nextdevs.GestioneEventi.DTO.UserDTO;
import it.nextdevs.GestioneEventi.entity.Event;
import it.nextdevs.GestioneEventi.entity.User;
import it.nextdevs.GestioneEventi.enums.Role;
import it.nextdevs.GestioneEventi.exception.BadRequestException;
import it.nextdevs.GestioneEventi.exception.UserNotFoundException;
import it.nextdevs.GestioneEventi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String saveUser(UserDTO userDTO) {
        if(getUserByEmail(userDTO.getEmail()).isEmpty()) {
            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            user.setRole(Role.USER);
            user.setPassword( passwordEncoder.encode(userDTO.getPassword()));
            userRepository.save(user);
            return "Utente with id=" + user.getId() + "correctly saved";
        }else{
            throw new BadRequestException("L'utente con email "+ userDTO.getEmail()+" già esistente");
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(int id) {
        System.out.println(id);
        return userRepository.findById(id);
    }

    public User updateUser(int id, UserDTO userDTO) {
        Optional<User> userOptional = getUserById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            return userRepository.save(user);
        }else{
            throw new UserNotFoundException("User with id:" + id + " not found");
        }
    }

    public String deleteUser(int id) {
        Optional<User> userOptional = getUserById(id);

        if (userOptional.isPresent()) {
            userRepository.delete(userOptional.get());
            return "User with id:" + id + " correctly deleted";
        }else{
            throw new UserNotFoundException("User with id:" + id + " not found");
        }
    }

    public Optional<User> getUserByEmail(String email){
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional;
    }

    public List<Event> getEventiPrenotati(int id) {
        Optional<User> utenteOptional = getUserById(id);
        if (utenteOptional.isPresent()) {
            return utenteOptional.get().getEventsReserved();
        } else {
            throw new UserNotFoundException("Utente with id=" + id + " not found");
        }
    }

    public String annullaPrenotazione(int utenteId, int eventoId) {
        Optional<User> utenteOptional = getUserById(utenteId);
        if (utenteOptional.isPresent()) {
            User utente = utenteOptional.get();
            utente.annullaPrenotazione(eventoId);
            userRepository.save(utente);
            return "Prenotazione annullata con successo";
        } else {
            throw new UserNotFoundException("Utente with id=" + utenteId + " not found");
        }
    }
}
