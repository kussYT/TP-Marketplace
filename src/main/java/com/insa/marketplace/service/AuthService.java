package com.insa.marketplace.service;

import com.insa.marketplace.dto.LoginRequest;
import com.insa.marketplace.dto.UserDto;
import com.insa.marketplace.mapper.UserMapper;
import com.insa.marketplace.model.User;
import com.insa.marketplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Login :
     * - si l'utilisateur existe, on vérifie le mot de passe
     * - sinon on le crée
     */
    public UserDto login(LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        Optional<User> existingUser = userRepository.findByUsername(username);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            if (user.getPassword() != null && !user.getPassword().equals(password)) {
                throw new IllegalArgumentException("Invalid credentials");
            }
            return userMapper.toDto(user);
        }

        // Création d'un nouvel utilisateur si inexistant
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setEmail(null);

        User saved = userRepository.save(newUser);
        return userMapper.toDto(saved);
    }
}
