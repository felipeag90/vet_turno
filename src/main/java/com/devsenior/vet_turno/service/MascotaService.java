package com.devsenior.vet_turno.service;

import com.devsenior.vet_turno.dto.MascotaDTO;
import com.devsenior.vet_turno.dto.MascotaRequest;
import com.devsenior.vet_turno.model.Mascota;
import com.devsenior.vet_turno.model.Propietario;
import com.devsenior.vet_turno.repository.MascotaRepository;
import com.devsenior.vet_turno.repository.PropietarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MascotaService {

    private final MascotaRepository mascotaRepository;
    private final PropietarioRepository propietarioRepository;

    public MascotaService(MascotaRepository mascotaRepository, PropietarioRepository propietarioRepository) {
        this.mascotaRepository = mascotaRepository;
        this.propietarioRepository = propietarioRepository;
    }

    // 🐶 Crear mascota
    public MascotaDTO crearMascota(MascotaRequest request) {
        Propietario propietario = propietarioRepository.findById(request.getPropietarioId())
                .orElseThrow(() -> new IllegalArgumentException("Propietario no encontrado"));

        Mascota mascota = new Mascota();
        mascota.setNombre(request.getNombre());
        mascota.setEspecie(request.getEspecie());
        mascota.setRaza(request.getRaza());
        mascota.setPropietario(propietario);

        mascotaRepository.save(mascota);

        return new MascotaDTO(
                mascota.getId(),
                mascota.getNombre(),
                mascota.getEspecie(),
                mascota.getRaza(),
                propietario.getId(),
                propietario.getNombre()
        );
    }

    // 📋 Listar mascotas
    public List<MascotaDTO> listarMascotas() {
        return mascotaRepository.findAll().stream()
                .map(m -> new MascotaDTO(
                        m.getId(),
                        m.getNombre(),
                        m.getEspecie(),
                        m.getRaza(),
                        m.getPropietario().getId(),
                        m.getPropietario().getNombre()
                ))
                .collect(Collectors.toList());
    }
}
