package com.devsenior.vet_turno.service;

import com.devsenior.vet_turno.dto.AuthResponse;
import com.devsenior.vet_turno.dto.UsuarioDTO;
import com.devsenior.vet_turno.dto.UsuarioRequest;
import com.devsenior.vet_turno.model.Rol;
import com.devsenior.vet_turno.model.Usuario;
import com.devsenior.vet_turno.repository.UsuarioRepository;
import com.devsenior.vet_turno.security.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UsuarioDTO crearUsuario(UsuarioRequest request) {
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setUsernameField(request.getUsername());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(Rol.USER);

        usuarioRepository.save(usuario);

        return new UsuarioDTO(usuario.getId(), usuario.getNombre(), usuario.getEmail(), usuario.getRol());
    }

    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(u -> new UsuarioDTO(u.getId(), u.getNombre(), u.getEmail(), u.getRol()))
                .collect(Collectors.toList());
    }

    public AuthResponse registrarUsuario(UsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("El username ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setUsernameField(request.getUsername());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setRol(Rol.USER);

        usuarioRepository.save(usuario);

        // Generar token JWT
        String token = jwtService.generateToken(usuario);

        // Devolver respuesta con token y datos del usuario
        return new AuthResponse(
                token,
                usuario.getUsernameField(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol().name()
        );
    }
}
