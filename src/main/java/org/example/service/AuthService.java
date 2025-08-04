package org.example.service;

import jakarta.annotation.PostConstruct;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.example.utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // ✅ Register new user
    public User register(User user) {
        if (user.getEmail() == null || user.getEmail().trim().isEmpty())
            throw new RuntimeException("Email is required");

        if (userRepository.findByEmail(user.getEmail()).isPresent())
            throw new RuntimeException("Email already in use");

        if (user.getPassword() == null || user.getPassword().trim().isEmpty())
            throw new RuntimeException("Password cannot be empty");

        if (user.getRole() == null || user.getRole().isEmpty())
            user.setRole("USER");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // ✅ Login with password validation
    public String login(String email, String rawPassword) {
        return userRepository.findByEmail(email)
                .filter(user -> passwordEncoder.matches(rawPassword, user.getPassword()))
                .map(user -> jwtUtil.generateToken(user.getEmail(), user.getRole()))
                .orElse(null);
    }

    // ✅ Utility: Get user by email
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // ✅ Create default admin if missing
    @PostConstruct
    public void createAdminIfNotExists() {
        String adminEmail = "admin@cosmetics.com";
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            User admin = new User();
            admin.setName("Admin");
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            userRepository.save(admin);
            System.out.println("✅ Default admin created: admin@cosmetics.com / admin123");
        }
    }
}
