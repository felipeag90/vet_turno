package com.devsenior.vet_turno.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class CitaRequest {

    @NotNull(message = "La fecha y hora son obligatorias")
    @Future(message = "La fecha de la cita debe ser en el futuro")
    private LocalDateTime fechaHora;

    @NotBlank(message = "El motivo es obligatorio")
    private String motivo;

    @NotNull(message = "El ID de la mascota es obligatorio")
    private Long mascotaId;

    @NotNull(message = "El ID del veterinario es obligatorio")
    private Long veterinarioId;

    public CitaRequest() {
    }

    public CitaRequest(LocalDateTime fechaHora, String motivo, Long mascotaId, Long veterinarioId) {
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.mascotaId = mascotaId;
        this.veterinarioId = veterinarioId;
    }

    public @NotNull(message = "La fecha y hora son obligatorias") @Future(message = "La fecha de la cita debe ser en el futuro") LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Long getMascotaId() {
        return mascotaId;
    }

    public void setMascotaId(Long mascotaId) {
        this.mascotaId = mascotaId;
    }

    public Long getVeterinarioId() {
        return veterinarioId;
    }

    public void setVeterinarioId(Long veterinarioId) {
        this.veterinarioId = veterinarioId;
    }
}


