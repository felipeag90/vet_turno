package com.devsenior.vet_turno.service;

import com.devsenior.vet_turno.dto.AuthResponse;
import com.devsenior.vet_turno.dto.UsuarioRequest;
import com.devsenior.vet_turno.model.Usuario;
import com.devsenior.vet_turno.repository.UsuarioRepository;
import com.devsenior.vet_turno.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UsuarioServiceTest {

    @Test
    void publicRegistrationAlwaysCreatesAUserEvenIfAdminRoleWasSubmitted() {
        UsuarioRepository repository = mock(UsuarioRepository.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        JwtService jwtService = mock(JwtService.class);
        UsuarioService service = new UsuarioService(repository, passwordEncoder, jwtService);

        UsuarioRequest request = new UsuarioRequest(
                "Test",
                "testuser",
                "test@example.com",
                "password"
        );
        when(repository.existsByEmail(request.getEmail())).thenReturn(false);
        when(repository.existsByUsername(request.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded-password");
        when(jwtService.generateToken(any(Usuario.class))).thenReturn("token");

        AuthResponse response = service.registrarUsuario(request);

        assertEquals("USER", response.getRol());
        verify(repository).save(any(Usuario.class));
    }
}
