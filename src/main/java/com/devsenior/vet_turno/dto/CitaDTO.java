package com.devsenior.vet_turno.dto;

import java.time.LocalDateTime;

public class CitaDTO {
    private Long id;
    private LocalDateTime fechaHora;
    private LocalDateTime fechaHoraFin;
    private String motivo;
    private String mascotaNombre;
    private String propietarioNombre;
    private String veterinarioNombre;


    public CitaDTO(Long id, LocalDateTime fechaHora, LocalDateTime fechaHoraFin,
                   String motivo, String mascotaNombre,
                   String propietarioNombre, String veterinarioNombre) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.fechaHoraFin = fechaHoraFin;
        this.motivo = motivo;
        this.mascotaNombre = mascotaNombre;
        this.propietarioNombre = propietarioNombre;
        this.veterinarioNombre = veterinarioNombre;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getMascotaNombre() {
        return mascotaNombre;
    }

    public void setMascotaNombre(String mascotaNombre) {
        this.mascotaNombre = mascotaNombre;
    }

    public String getPropietarioNombre() {
        return propietarioNombre;
    }

    public void setPropietarioNombre(String propietarioNombre) {
        this.propietarioNombre = propietarioNombre;
    }

    public String getVeterinarioNombre() {
        return veterinarioNombre;
    }

    public void setVeterinarioNombre(String veterinarioNombre) {
        this.veterinarioNombre = veterinarioNombre;
    }


}
