package com.api.Agro.controller;

import com.api.Agro.config.JwtUtil;
import com.api.Agro.dtos.AuthRequest;
import com.api.Agro.dtos.AuthResponse;
import com.api.Agro.service.UsuarioDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioDetailsService usuarioDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            UserDetails userDetails = usuarioDetailsService.loadUserByUsername(request.getUsername());
            String token = jwtUtil.generateToken(userDetails.getUsername()); // corrigido aqui

            return ResponseEntity.ok(new AuthResponse(token));
        } catch (AuthenticationException e) {
            // 🔴 Aqui exibe a causa real no console
            System.err.println("Erro de autenticação: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            e.printStackTrace();  // opcional, mostra a pilha toda
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Erro de autenticação: " + e.getMessage());
        }
    }
}
