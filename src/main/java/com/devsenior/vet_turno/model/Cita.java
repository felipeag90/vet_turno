package com.devsenior.vet_turno.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "citas",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_citas_veterinario_fechahora",
                        columnNames = {"veterinario_Id", "fechahora"}
                ),
                @UniqueConstraint(
                        name = "uq_citas_mascota_fechahora",
                        columnNames = {"mascota_Id", "fechahora"}
                )
        }
)
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "fechahora")
    private LocalDateTime fechaHora;

    @Column
    private String motivo;

    @ManyToOne
    @JoinColumn(name = "mascota_Id")
    private Mascota mascota;

    @ManyToOne
    @JoinColumn(name = "veterinario_Id")
    private Veterinario veterinario;

    public Cita() {
    }


//getters y setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechahora) {
        this.fechaHora = fechahora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
            this.veterinario = veterinario;
    }


}
