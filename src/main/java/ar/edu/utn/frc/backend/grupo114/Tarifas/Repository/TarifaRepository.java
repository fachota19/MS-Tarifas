package ar.edu.utn.frc.backend.grupo114.Tarifas.Repository;

// Import del modelo desde su paquete correcto
import ar.edu.utn.frc.backend.grupo114.Model.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Long> {

    // Ejemplo: Buscar una tarifa activa por nombre y fecha
    Optional<Tarifa> findByNombreAndActivaIsTrueAndFechaInicioBeforeAndFechaFinAfter(
            String nombre, LocalDate fecha
    );
}