package it.nextdevs.GestioneEventi.service;

import it.nextdevs.GestioneEventi.DTO.UserLoginDTO;
import it.nextdevs.GestioneEventi.entity.User;
import it.nextdevs.GestioneEventi.exception.UnauthorizedException;
import it.nextdevs.GestioneEventi.security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtTool jwtTool;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authenticateUserAndCreateToken(UserLoginDTO userLoginDTO) {
        Optional<User> dipendenteOptional = userService.getUserByEmail(userLoginDTO.getEmail());
        if (dipendenteOptional.isEmpty()) {
            throw new UnauthorizedException("Error in authorization, relogin!");
        } else {
            User utente = dipendenteOptional.get();
            if (passwordEncoder.matches(userLoginDTO.getPassword(), utente.getPassword())) {
                return jwtTool.createToken(utente);
            } else {
                throw new UnauthorizedException("Error in authorization, relogin!");
            }
        }
    }
}
