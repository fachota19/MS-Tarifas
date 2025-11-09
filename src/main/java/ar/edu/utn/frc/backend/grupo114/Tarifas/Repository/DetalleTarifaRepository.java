package ar.edu.utn.frc.backend.grupo114.Tarifas.Repository; // <-- Corregido a minúscula

import ar.edu.utn.frc.backend.grupo114.Model.DetalleTarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DetalleTarifaRepository extends JpaRepository<DetalleTarifa, Long> {

    Optional<DetalleTarifa> findByConcepto(String concepto);

}