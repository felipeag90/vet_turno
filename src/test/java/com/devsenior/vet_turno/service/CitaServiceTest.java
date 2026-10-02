package com.devsenior.vet_turno.service;

import com.devsenior.vet_turno.dto.CitaDTO;
import com.devsenior.vet_turno.dto.CitaRequest;
import com.devsenior.vet_turno.model.Cita;
import com.devsenior.vet_turno.model.Mascota;
import com.devsenior.vet_turno.model.Propietario;
import com.devsenior.vet_turno.model.Rol;
import com.devsenior.vet_turno.model.Usuario;
import com.devsenior.vet_turno.model.Veterinario;
import com.devsenior.vet_turno.repository.CitaRepository;
import com.devsenior.vet_turno.repository.MascotaRepository;
import com.devsenior.vet_turno.repository.VeterinarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDateTime;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CitaServiceTest {

    @Test
    void veterinarianOnlyLoadsAppointmentsAssignedToTheirAccount() {
        CitaRepository citaRepository = mock(CitaRepository.class);
        VeterinarioRepository veterinarioRepository = mock(VeterinarioRepository.class);
        CitaService service = new CitaService(
                citaRepository,
                mock(MascotaRepository.class),
                veterinarioRepository
        );
        Usuario veterinarian = new Usuario();
        veterinarian.setId(42L);
        veterinarian.setRol(Rol.VET);
        when(veterinarioRepository.existsByUsuarioId(42L)).thenReturn(true);

        service.listarCitas(veterinarian);

        verify(citaRepository).findAllByVeterinarioUsuarioIdOrderByFechaHoraAsc(42L);
        verify(citaRepository, never()).findAll();
    }

    @Test
    void veterinarianWithoutLinkedProfileIsDenied() {
        CitaRepository citaRepository = mock(CitaRepository.class);
        VeterinarioRepository veterinarioRepository = mock(VeterinarioRepository.class);
        CitaService service = new CitaService(
                citaRepository,
                mock(MascotaRepository.class),
                veterinarioRepository
        );
        Usuario veterinarian = new Usuario();
        veterinarian.setId(42L);
        veterinarian.setRol(Rol.VET);
        when(veterinarioRepository.existsByUsuarioId(42L)).thenReturn(false);

        assertThrows(AccessDeniedException.class, () -> service.listarCitas(veterinarian));
        verify(citaRepository, never()).findAll();
    }

    @Test
    void rejectsAppointmentThatWouldEndAfterClosingTime() {
        CitaRepository citaRepository = mock(CitaRepository.class);
        CitaService service = service(citaRepository, mock(MascotaRepository.class),
                mock(VeterinarioRepository.class));

        assertThrows(IllegalArgumentException.class, () -> service.crearCita(
                requestAt(LocalDateTime.of(2027, 1, 15, 17, 1))
        ));
        verify(citaRepository, never()).save(org.mockito.ArgumentMatchers.any(Cita.class));
    }

    @Test
    void rejectsAppointmentWhenVeterinarianHasAnOverlappingAppointment() {
        CitaRepository citaRepository = mock(CitaRepository.class);
        LocalDateTime appointmentTime = LocalDateTime.of(2027, 1, 15, 10, 0);
        when(citaRepository.existsByVeterinarioIdAndFechaHoraLessThanAndFechaHoraGreaterThan(
                7L, appointmentTime.plusHours(1), appointmentTime.minusHours(1)
        )).thenReturn(true);
        CitaService service = service(citaRepository, mock(MascotaRepository.class),
                mock(VeterinarioRepository.class));

        assertThrows(IllegalArgumentException.class, () -> service.crearCita(requestAt(appointmentTime)));
        verify(citaRepository, never()).save(org.mockito.ArgumentMatchers.any(Cita.class));
    }

    @Test
    void rejectsAppointmentWhenPetHasAnOverlappingAppointment() {
        CitaRepository citaRepository = mock(CitaRepository.class);
        LocalDateTime appointmentTime = LocalDateTime.of(2027, 1, 15, 10, 0);
        when(citaRepository.existsByVeterinarioIdAndFechaHoraLessThanAndFechaHoraGreaterThan(
                7L, appointmentTime.plusHours(1), appointmentTime.minusHours(1)
        )).thenReturn(false);
        when(citaRepository.existsByMascotaIdAndFechaHoraLessThanAndFechaHoraGreaterThan(
                5L, appointmentTime.plusHours(1), appointmentTime.minusHours(1)
        )).thenReturn(true);
        CitaService service = service(citaRepository, mock(MascotaRepository.class),
                mock(VeterinarioRepository.class));

        assertThrows(IllegalArgumentException.class, () -> service.crearCita(requestAt(appointmentTime)));
        verify(citaRepository, never()).save(org.mockito.ArgumentMatchers.any(Cita.class));
    }

    @Test
    void acceptedAppointmentReturnsTimeAndVeterinarianName() {
        CitaRepository citaRepository = mock(CitaRepository.class);
        MascotaRepository mascotaRepository = mock(MascotaRepository.class);
        VeterinarioRepository veterinarioRepository = mock(VeterinarioRepository.class);
        LocalDateTime appointmentTime = LocalDateTime.of(2027, 1, 15, 8, 0);
        CitaRequest request = requestAt(appointmentTime);
        request.setMotivo("Control");

        Propietario propietario = new Propietario();
        propietario.setNombre("Paula");
        Mascota mascota = new Mascota();
        mascota.setNombre("Luna");
        mascota.setPropietario(propietario);
        Veterinario veterinarian = new Veterinario();
        veterinarian.setNombre("Andres");

        when(citaRepository.existsByVeterinarioIdAndFechaHoraLessThanAndFechaHoraGreaterThan(
                7L, appointmentTime.plusHours(1), appointmentTime.minusHours(1)
        )).thenReturn(false);
        when(citaRepository.existsByMascotaIdAndFechaHoraLessThanAndFechaHoraGreaterThan(
                5L, appointmentTime.plusHours(1), appointmentTime.minusHours(1)
        )).thenReturn(false);
        when(mascotaRepository.findById(5L)).thenReturn(java.util.Optional.of(mascota));
        when(veterinarioRepository.findById(7L)).thenReturn(java.util.Optional.of(veterinarian));

        CitaDTO created = service(citaRepository, mascotaRepository, veterinarioRepository).crearCita(request);

        assertEquals(appointmentTime, created.getFechaHora());
        assertEquals(appointmentTime.plusHours(1), created.getFechaHoraFin());
        assertEquals("Andres", created.getVeterinarioNombre());
    }

    @Test
    void allowsAppointmentEndingExactlyAtClosingTime() {
        CitaRepository citaRepository = mock(CitaRepository.class);
        MascotaRepository mascotaRepository = mock(MascotaRepository.class);
        VeterinarioRepository veterinarioRepository = mock(VeterinarioRepository.class);
        LocalDateTime appointmentTime = LocalDateTime.of(2027, 1, 15, 17, 0);
        when(citaRepository.existsByVeterinarioIdAndFechaHoraLessThanAndFechaHoraGreaterThan(
                7L, appointmentTime.plusHours(1), appointmentTime.minusHours(1)
        )).thenReturn(false);
        when(citaRepository.existsByMascotaIdAndFechaHoraLessThanAndFechaHoraGreaterThan(
                5L, appointmentTime.plusHours(1), appointmentTime.minusHours(1)
        )).thenReturn(false);
        Mascota mascota = new Mascota();
        mascota.setNombre("Luna");
        Propietario propietario = new Propietario();
        propietario.setNombre("Paula");
        mascota.setPropietario(propietario);
        Veterinario veterinarian = new Veterinario();
        veterinarian.setNombre("Andres");
        when(mascotaRepository.findById(5L)).thenReturn(java.util.Optional.of(mascota));
        when(veterinarioRepository.findById(7L)).thenReturn(java.util.Optional.of(veterinarian));

        CitaDTO created = service(citaRepository, mascotaRepository, veterinarioRepository)
                .crearCita(requestAt(appointmentTime));

        assertEquals(appointmentTime.plus(Duration.ofHours(1)), created.getFechaHoraFin());
    }

    private CitaService service(CitaRepository citaRepository,
                               MascotaRepository mascotaRepository,
                               VeterinarioRepository veterinarioRepository) {
        return new CitaService(citaRepository, mascotaRepository, veterinarioRepository);
    }

    private CitaRequest requestAt(LocalDateTime fechaHora) {
        return new CitaRequest(fechaHora, "Consulta", 5L, 7L);
    }
}
