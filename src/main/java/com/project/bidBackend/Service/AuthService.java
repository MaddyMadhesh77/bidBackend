package com.project.bidBackend.Service;

import com.project.bidBackend.Config.JwtUtil;
import com.project.bidBackend.Model.Role;
import com.project.bidBackend.Model.User;
import com.project.bidBackend.Repo.UserRepo;
import com.project.bidBackend.dto.LoginRequest;
import com.project.bidBackend.dto.LoginResponse;
import com.project.bidBackend.dto.RegisterRequest;
import com.project.bidBackend.Config.AuthConfig;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepo userRepo;
    private final AuthConfig authConfig;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepo userRepo,AuthConfig authConfig, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.authConfig = authConfig;
        this.jwtUtil = jwtUtil;
    }

    public void register(RegisterRequest request) {

        if (userRepo.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(authConfig.passwordEncoder().encode(request.getPassword()));
        user.setRole(Role.BIDDER); // default role

        userRepo.save(user);
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!authConfig.passwordEncoder().matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setUserId(user.getId());
        response.setRole(user.getRole().name());
        response.setMessage("Login successful");

        return response;
    }
}
