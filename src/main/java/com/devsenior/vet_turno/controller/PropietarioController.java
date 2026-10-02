package com.devsenior.vet_turno.controller;

import com.devsenior.vet_turno.dto.PropietarioDTO;
import com.devsenior.vet_turno.dto.PropietarioRequest;
import com.devsenior.vet_turno.service.PropietarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/propietarios")
public class PropietarioController {

    private final PropietarioService propietarioService;

    public PropietarioController(PropietarioService propietarioService) {
        this.propietarioService = propietarioService;
    }

    // Crear propietario
    @PostMapping
    public ResponseEntity<?> crearPropietario(@RequestBody PropietarioRequest request) {
        try {
            PropietarioDTO propietario = propietarioService.crearPropietario(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(propietario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el propietario: " + e.getMessage());
        }
    }

    // Listar propietarios
    @GetMapping
    public ResponseEntity<List<PropietarioDTO>> listarPropietarios() {
        List<PropietarioDTO> propietarios = propietarioService.listarPropietarios();
        return ResponseEntity.ok(propietarios);
    }
}
