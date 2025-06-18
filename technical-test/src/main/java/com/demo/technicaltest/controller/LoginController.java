package com.demo.technicaltest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import com.demo.technicaltest.dto.RegisterDto;
import com.demo.technicaltest.dto.LoginDto;
import com.demo.technicaltest.services.UserService;
import com.demo.technicaltest.security.JwtUtil;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/public")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDto request) {
        userService.registerUser(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(java.util.Map.of("message", "Usuario creado exitosamente"));
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto request) {
    try {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );

        if (authentication.isAuthenticated()) {
            String token = jwtUtil.generateToken(request.getUsername());
            return ResponseEntity.ok().body(
                java.util.Map.of("token", token)  
            );
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(java.util.Map.of("message", "Credenciales inválidas"));
        }

    } catch (AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body(java.util.Map.of("message", "Credenciales incorrectas"));
    }
    }

}
