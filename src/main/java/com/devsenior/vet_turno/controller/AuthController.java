package com.devsenior.vet_turno.controller;

import com.devsenior.vet_turno.dto.AuthResponse;
import com.devsenior.vet_turno.dto.LoginRequest;
import com.devsenior.vet_turno.dto.UsuarioRequest;
import com.devsenior.vet_turno.service.AuthService;
import com.devsenior.vet_turno.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UsuarioService usuarioService;

    public AuthController(AuthService authService, UsuarioService usuarioService) {
        this.authService = authService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody UsuarioRequest request) {
        // Registrar usuario y devolver token
        AuthResponse response = usuarioService.registrarUsuario(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
