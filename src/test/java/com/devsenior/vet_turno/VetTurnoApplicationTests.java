package com.devsenior.vet_turno;

import com.devsenior.vet_turno.model.Cita;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class VetTurnoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void citaStoresFechaHora() {
		LocalDateTime fechaHora = LocalDateTime.of(2027, 1, 15, 10, 30);
		Cita cita = new Cita();

		cita.setFechaHora(fechaHora);

		assertEquals(fechaHora, cita.getFechaHora());
	}

}
