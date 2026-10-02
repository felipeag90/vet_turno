package com.devsenior.vet_turno.dto;

public class AuthResponse {
    private String token;
    private String username;
    private String nombre;
    private String email;
    private String rol;

    public AuthResponse(String token, String username, String nombre, String email, String rol) {
        this.token = token;
        this.username = username;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
    }

    // Getters y setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}
