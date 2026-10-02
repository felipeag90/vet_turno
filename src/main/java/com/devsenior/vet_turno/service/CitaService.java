package com.devsenior.vet_turno.service;

import com.devsenior.vet_turno.dto.CitaDTO;
import com.devsenior.vet_turno.dto.CitaRequest;
import com.devsenior.vet_turno.model.Cita;
import com.devsenior.vet_turno.model.Mascota;
import com.devsenior.vet_turno.model.Rol;
import com.devsenior.vet_turno.model.Usuario;
import com.devsenior.vet_turno.model.Veterinario;
import com.devsenior.vet_turno.repository.CitaRepository;
import com.devsenior.vet_turno.repository.MascotaRepository;
import com.devsenior.vet_turno.repository.VeterinarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CitaService {

    private final CitaRepository citaRepository;
    private final MascotaRepository mascotaRepository;
    private final VeterinarioRepository veterinarioRepository;

    private static final LocalTime HORA_APERTURA = LocalTime.of(8, 0);
    private static final LocalTime HORA_CIERRE = LocalTime.of(18, 0);
    private static final Duration DURACION_CITA = Duration.ofHours(1);

    public CitaService(CitaRepository citaRepository,
                       MascotaRepository mascotaRepository,
                       VeterinarioRepository veterinarioRepository) {
        this.citaRepository = citaRepository;
        this.mascotaRepository = mascotaRepository;
        this.veterinarioRepository = veterinarioRepository;
    }

    public CitaDTO crearCita(CitaRequest request) {
        LocalDateTime fechaHora = request.getFechaHora();
        LocalDateTime fechaHoraFin = fechaHora.plus(DURACION_CITA);
        LocalTime horaCita = request.getFechaHora().toLocalTime();
        if (horaCita.isBefore(HORA_APERTURA) || fechaHoraFin.toLocalTime().isAfter(HORA_CIERRE)
                || !fechaHoraFin.toLocalDate().equals(fechaHora.toLocalDate())) {
            throw new IllegalArgumentException(
                    "Las citas duran 1 hora y deben estar dentro del horario de 8:00 a 18:00."
            );
        }

        LocalDateTime ultimaHoraInicioEnConflicto = fechaHora.minus(DURACION_CITA);
        boolean ocupado = citaRepository.existsByVeterinarioIdAndFechaHoraLessThanAndFechaHoraGreaterThan(
                request.getVeterinarioId(),
                fechaHoraFin,
                ultimaHoraInicioEnConflicto
        );
        if (ocupado) {
            throw new IllegalArgumentException("El veterinario ya tiene una cita que se cruza con ese horario.");
        }

        if (citaRepository.existsByMascotaIdAndFechaHoraLessThanAndFechaHoraGreaterThan(
                request.getMascotaId(),
                fechaHoraFin,
                ultimaHoraInicioEnConflicto
        )) {
            throw new IllegalArgumentException("La mascota ya tiene una cita que se cruza con ese horario.");
        }

        Mascota mascota = mascotaRepository.findById(request.getMascotaId())
                .orElseThrow(() -> new IllegalArgumentException("Mascota no encontrada"));

        Veterinario veterinario = veterinarioRepository.findById(request.getVeterinarioId())
                .orElseThrow(() -> new IllegalArgumentException("Veterinario no encontrado"));

        Cita cita = new Cita();
        cita.setFechaHora(request.getFechaHora());
        cita.setMotivo(request.getMotivo());
        cita.setMascota(mascota);
        cita.setVeterinario(veterinario);

        citaRepository.save(cita);

        return new CitaDTO(
                cita.getId(),
                cita.getFechaHora(),
                cita.getFechaHora().plus(DURACION_CITA),
                cita.getMotivo(),
                mascota.getNombre(),
                mascota.getPropietario().getNombre(),
                veterinario.getNombre()
        );
    }

    public List<CitaDTO> listarCitas(Usuario usuario) {
        List<Cita> citas;
        if (usuario.getRol() == Rol.VET) {
            if (!veterinarioRepository.existsByUsuarioId(usuario.getId())) {
                throw new AccessDeniedException("La cuenta veterinaria no está asociada a un perfil de veterinario.");
            }
            citas = citaRepository.findAllByVeterinarioUsuarioIdOrderByFechaHoraAsc(usuario.getId());
        } else {
            citas = citaRepository.findAllByOrderByFechaHoraAsc();
        }

        return citas.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private CitaDTO toDto(Cita cita) {
        return new CitaDTO(
                cita.getId(),
                cita.getFechaHora(),
                cita.getFechaHora().plus(DURACION_CITA),
                cita.getMotivo(),
                cita.getMascota().getNombre(),
                cita.getMascota().getPropietario().getNombre(),
                cita.getVeterinario().getNombre()
        );
    }
}
