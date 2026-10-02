package com.devsenior.vet_turno.controller;

import com.devsenior.vet_turno.dto.CitaDTO;
import com.devsenior.vet_turno.dto.CitaRequest;
import com.devsenior.vet_turno.model.Usuario;
import com.devsenior.vet_turno.service.CitaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    //Crear cita
    @PostMapping
    public ResponseEntity<?> crearCita(@Valid @RequestBody CitaRequest request) {
        CitaDTO cita = citaService.crearCita(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cita);
    }

    // Listar citas
    @GetMapping
    public ResponseEntity<List<CitaDTO>> listarCitas(@AuthenticationPrincipal Usuario usuario) {
        List<CitaDTO> citas = citaService.listarCitas(usuario);
        return ResponseEntity.ok(citas);
    }
}
