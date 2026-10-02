package com.devsenior.vet_turno.service;

import com.devsenior.vet_turno.dto.PropietarioDTO;
import com.devsenior.vet_turno.dto.PropietarioRequest;
import com.devsenior.vet_turno.model.Propietario;
import com.devsenior.vet_turno.repository.PropietarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PropietarioService {

    private final PropietarioRepository propietarioRepository;

    public PropietarioService(PropietarioRepository propietarioRepository) {
        this.propietarioRepository = propietarioRepository;
    }

    public PropietarioDTO crearPropietario(PropietarioRequest request) {
        Propietario propietario = new Propietario();
        propietario.setNombre(request.getNombre());
        propietario.setTelefono(request.getTelefono());
        propietario.setEmail(request.getEmail());
        propietarioRepository.save(propietario);

        return new PropietarioDTO(
                propietario.getId(),
                propietario.getNombre(),
                propietario.getTelefono(),
                propietario.getEmail()
        );
    }

    public List<PropietarioDTO> listarPropietarios() {
        return propietarioRepository.findAll().stream()
                .map(p -> new PropietarioDTO(
                        p.getId(),
                        p.getNombre(),
                        p.getTelefono(),
                        p.getEmail()
                ))
                .collect(Collectors.toList());
    }
}

