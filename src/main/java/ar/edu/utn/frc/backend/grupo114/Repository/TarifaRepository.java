import com.tpi.tarifas.model.Tarifa; // (Asegúrate que el import sea tu clase Tarifa)
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Long> {

    // Spring Data JPA es inteligente. Si sigues la convención de nombres,
    // él solo crea la consulta.

    // Ejemplo: Buscar una tarifa activa por nombre y fecha
    Optional<Tarifa> findByNombreAndActivaIsTrueAndFechaInicioBeforeAndFechaFinAfter(
            String nombre, LocalDate fecha
    );
}