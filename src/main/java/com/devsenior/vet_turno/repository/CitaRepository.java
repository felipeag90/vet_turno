package com.devsenior.vet_turno.repository;

import com.devsenior.vet_turno.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    boolean existsByVeterinarioIdAndFechaHoraLessThanAndFechaHoraGreaterThan(
            Long veterinarioId,
            LocalDateTime appointmentEnd,
            LocalDateTime appointmentStartMinusDuration
    );

    boolean existsByMascotaIdAndFechaHoraLessThanAndFechaHoraGreaterThan(
            Long mascotaId,
            LocalDateTime appointmentEnd,
            LocalDateTime appointmentStartMinusDuration
    );

    List<Cita> findAllByOrderByFechaHoraAsc();

    List<Cita> findAllByVeterinarioUsuarioIdOrderByFechaHoraAsc(Long usuarioId);
}