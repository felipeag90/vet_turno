package com.devsenior.vet_turno.security;

import com.devsenior.vet_turno.model.Rol;
import com.devsenior.vet_turno.model.Usuario;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtServiceTest {

    private final JwtService jwtService = new JwtService();

    @Test
    void generatedTokenHasNoExpirationAndCanBeValidated() {
        Usuario usuario = new Usuario();
        usuario.setEmail("usuario@example.com");
        usuario.setRol(Rol.USER);

        String token = jwtService.generateToken(usuario);
        String[] segments = token.split("\\.");
        String claims = new String(
                Base64.getUrlDecoder().decode(segments[1]),
                StandardCharsets.UTF_8
        );

        assertFalse(claims.contains("\"exp\""));
        assertTrue(jwtService.esValido(token));
        assertEquals("usuario@example.com", jwtService.extraerEmail(token));
    }

    @Test
    void eachUserGetsATokenForTheirOwnIdentity() {
        Usuario first = new Usuario();
        first.setEmail("first@example.com");
        first.setRol(Rol.USER);

        Usuario second = new Usuario();
        second.setEmail("second@example.com");
        second.setRol(Rol.ADMIN);

        assertEquals("first@example.com", jwtService.extraerEmail(jwtService.generateToken(first)));
        assertEquals("second@example.com", jwtService.extraerEmail(jwtService.generateToken(second)));
    }

    @Test
    void invalidTokenIsRejected() {
        assertFalse(jwtService.esValido("not-a-jwt"));
    }
}
