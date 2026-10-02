package com.devsenior.vet_turno.repository;

import com.devsenior.vet_turno.model.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {
    Optional<Veterinario> findByUsuarioId(Long usuarioId);
    boolean existsByUsuarioId(Long usuarioId);
}
