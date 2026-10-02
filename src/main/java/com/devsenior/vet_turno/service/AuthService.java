package com.devsenior.vet_turno.service;

import com.devsenior.vet_turno.dto.AuthResponse;
import com.devsenior.vet_turno.dto.LoginRequest;
import com.devsenior.vet_turno.model.Usuario;
import com.devsenior.vet_turno.repository.UsuarioRepository;
import com.devsenior.vet_turno.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }

        if (usuario.getRol() == null) {
            throw new IllegalStateException("La cuenta no tiene un rol asignado.");
        }

        // Token fijo sin expiración
        String token = jwtService.generateToken(usuario);

        return new AuthResponse(
                token,
                usuario.getUsernameField(), // username de la BD
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol().name()
        );

    }
}
