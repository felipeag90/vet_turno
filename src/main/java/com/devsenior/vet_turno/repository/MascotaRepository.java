package com.devsenior.vet_turno.repository;

import com.devsenior.vet_turno.model.Mascota;
import com.devsenior.vet_turno.model.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    List<Mascota> findByPropietario(Propietario propietario);
}
