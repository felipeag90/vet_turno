package com.devsenior.vet_turno.model;


import jakarta.persistence.*;

@Entity
@Table(name = "mascotas")
public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String nombre;
    private String raza;

    @Column
    private String especie;

    @ManyToOne
    @JoinColumn(name = "propietario_id")
    private Propietario propietario;

    public Mascota(Propietario propietario) {
        this.propietario = propietario;
    }

    //constructors//
    public Mascota(long id, String nombre, String raza, String especie, Propietario propietario) {
        this.id = id;
        this.nombre = nombre;
        this.raza = raza;
        this.propietario = propietario;
        this.especie = especie;

    }

    public Mascota() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

}
