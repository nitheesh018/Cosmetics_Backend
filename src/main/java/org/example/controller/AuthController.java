package org.example.controller;

import org.example.model.User;
import org.example.service.AuthService;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    // ✅ Register new user
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User registered = authService.register(user);
            return ResponseEntity.ok(Map.of(
                    "message", "Registration successful",
                    "user", Map.of(
                            "id", registered.getId(),
                            "name", registered.getName(),
                            "email", registered.getEmail(),
                            "role", registered.getRole()
                    )
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // ✅ Login user and return token + user
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        // Authenticate
        String token = authService.login(email, password);
        if (token == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid email or password"));
        }

        // Fetch user
        return userRepository.findByEmail(email)
                .map(user -> ResponseEntity.ok(Map.of(
                        "token", token,
                        "user", Map.of(
                                "id", user.getId(),
                                "name", user.getName(),
                                "email", user.getEmail(),
                                "role", user.getRole()
                        )
                )))
                .orElseGet(() -> ResponseEntity.status(404).body(Map.of("error", "User not found")));
    }
}
