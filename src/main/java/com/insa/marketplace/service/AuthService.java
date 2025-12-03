package com.insa.marketplace.service;

import com.insa.marketplace.dto.LoginRequest;
import com.insa.marketplace.dto.UserDto;
import com.insa.marketplace.model.User;
import com.insa.marketplace.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto login(LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        Optional<User> existing = userRepository.findByUsername(username);

        if (existing.isPresent()) {
            User user = existing.get();

            // Ici on fait un check très simple
            if (user.getPassword() != null && !user.getPassword().equals(password)) {
                throw new IllegalArgumentException("Invalid credentials");
            }

            return toDto(user);
        }

        // Si l'utilisateur n'existe pas, on le crée
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        // email facultatif : on peut le laisser null
        newUser.setEmail(null);

        User saved = userRepository.save(newUser);
        return toDto(saved);
    }

    private UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
