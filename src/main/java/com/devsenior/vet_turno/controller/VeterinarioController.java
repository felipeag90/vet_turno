package com.devsenior.vet_turno.controller;

import com.devsenior.vet_turno.dto.VeterinarioDTO;
import com.devsenior.vet_turno.dto.VeterinarioRequest;
import com.devsenior.vet_turno.service.VeterinarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veterinarios")
public class VeterinarioController {

    private final VeterinarioService veterinarioService;

    public VeterinarioController(VeterinarioService veterinarioService) {
        this.veterinarioService = veterinarioService;
    }

    // Crear veterinario
    @PostMapping
    public ResponseEntity<?> crearVeterinario(@Valid @RequestBody VeterinarioRequest request) {
        try {
            VeterinarioDTO veterinario = (VeterinarioDTO) veterinarioService.crearVeterinario(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(veterinario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el veterinario: " + e.getMessage());
        }
    }

    // Listar veterinarios
    @GetMapping
    public ResponseEntity<List<VeterinarioDTO>> listarVeterinarios() {
        List<VeterinarioDTO> veterinarios = veterinarioService.listarVeterinarios();
        return ResponseEntity.ok(veterinarios);
    }
}
