# Proyecto Vet Turno 🐾

## 1. Descripción
Sistema de gestión de citas veterinarias desarrollado con **Spring Boot** y **MySQL**, que permite administrar propietarios, mascotas, veterinarios y citas.  
El proyecto implementa autenticación con JWT y control de roles (USER, ADMIN, VET), garantizando seguridad y persistencia de datos.

---

## 2. Matriz de entidades y relaciones

| Entidad       | Atributos principales                  | Relaciones                                   |
|---------------|----------------------------------------|----------------------------------------------|
| Propietario   | id, nombre, teléfono, email            | 1 Propietario → N Mascotas                   |
| Mascota       | id, nombre, especie, propietarioId     | N Mascotas → 1 Propietario                   |
| Veterinario   | id, nombre, especialidad, email        | 1 Veterinario → N Citas                      |
| Cita          | id, fechaHora, motivo, mascotaId, vetId| 1 Cita → 1 Mascota, 1 Veterinario            |
| Usuario       | id, username, password(hash), rol      | Control de acceso a endpoints (USER/ADMIN/VET)|

---

## 3. Requisitos previos
- Java 17+
- Maven
- MySQL
- Postman o Swagger para pruebas

---

## 4. Instalación y ejecución

4. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/usuario/vet-turno.git
   cd vet-turno

## 5. Licencia y Copyright
© 2026 Duvan Agudelo

## 6. ## Usuarios de prueba

El sistema incluye 3 usuarios iniciales en la base de datos, todos con contraseña `1234`:

- **paula usuario** → username: `paula@huellitas.com`, password: `1234`
- **marta admin** → username: `marta@huellitas.com`, password: `1234`
- **veterinario vet** → username: `andres@huellitas.com`, password: `1234`


