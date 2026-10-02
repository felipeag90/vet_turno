package com.devsenior.vet_turno.dto;

public class MascotaDTO {
        private Long id;
        private String nombre;
        private String especie;
        private String raza;
        private Long propietarioId;
        private String propietarioNombre;

        public MascotaDTO(Long id, String nombre, String especie, String raza,
                          Long propietarioId, String propietarioNombre) {
            this.id = id;
            this.nombre = nombre;
            this.especie = especie;
            this.raza = raza;
            this.propietarioId = propietarioId;
            this.propietarioNombre = propietarioNombre;
        }

        // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public Long getPropietarioId() {
        return propietarioId;
    }

    public void setPropietarioId(Long propietarioId) {
        this.propietarioId = propietarioId;
    }

    public String getPropietarioNombre() {
        return propietarioNombre;
    }

    public void setPropietarioNombre(String propietarioNombre) {
        this.propietarioNombre = propietarioNombre;
    }
}
