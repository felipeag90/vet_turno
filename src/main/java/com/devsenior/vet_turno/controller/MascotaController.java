package com.devsenior.vet_turno.controller;

import com.devsenior.vet_turno.dto.MascotaDTO;
import com.devsenior.vet_turno.dto.MascotaRequest;
import com.devsenior.vet_turno.service.MascotaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {

    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    // Crear mascota
    @PostMapping
    public ResponseEntity<?> crearMascota(@RequestBody MascotaRequest request) {
        try {
            MascotaDTO mascota = mascotaService.crearMascota(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(mascota);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear la mascota: " + e.getMessage());
        }
    }

    //Listar mascotas
    @GetMapping
    public ResponseEntity<List<MascotaDTO>> listarMascotas() {
        List<MascotaDTO> mascotas = mascotaService.listarMascotas();
        return ResponseEntity.ok(mascotas);
    }
}
