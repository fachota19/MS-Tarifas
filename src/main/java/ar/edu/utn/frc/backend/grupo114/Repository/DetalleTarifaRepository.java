import com.tpi.tarifas.model.DetalleTarifa; // (Asegúrate que el import sea tu clase DetalleTarifa)
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleTarifaRepository extends JpaRepository<DetalleTarifa, Long> {
    // Por ahora no necesitamos métodos custom, JpaRepository nos da todo.
}