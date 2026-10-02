package com.devsenior.vet_turno.service;

import com.devsenior.vet_turno.dto.VeterinarioDTO;
import com.devsenior.vet_turno.dto.VeterinarioRequest;
import com.devsenior.vet_turno.model.Rol;
import com.devsenior.vet_turno.model.Usuario;
import com.devsenior.vet_turno.model.Veterinario;
import com.devsenior.vet_turno.repository.UsuarioRepository;
import com.devsenior.vet_turno.repository.VeterinarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VeterinarioService {

    private final VeterinarioRepository veterinarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public VeterinarioService(VeterinarioRepository veterinarioRepository,
                              UsuarioRepository usuarioRepository,
                              PasswordEncoder passwordEncoder) {
        this.veterinarioRepository = veterinarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Crear veterinario
    public VeterinarioDTO crearVeterinario(VeterinarioRequest request) {
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
        usuario.setRol(Rol.VET);
        usuarioRepository.save(usuario);

        Veterinario veterinario = new Veterinario();
        veterinario.setNombre(request.getNombre());
        veterinario.setEspecialidad(request.getEspecialidad());
        veterinario.setUsuario(usuario);
        veterinarioRepository.save(veterinario);

        return new VeterinarioDTO(
                veterinario.getId(),
                veterinario.getNombre(),
                veterinario.getEspecialidad()
        );
    }

    // Listar veterinarios
    public List<VeterinarioDTO> listarVeterinarios() {
        return veterinarioRepository.findAll().stream()
                .map(v -> new VeterinarioDTO(
                        v.getId(),
                        v.getNombre(),
                        v.getEspecialidad()
                ))
                .collect(Collectors.toList());
    }
}
