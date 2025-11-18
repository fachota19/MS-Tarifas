package ar.edu.utn.frc.backend.grupo114.tarifas.repository;

import ar.edu.utn.frc.backend.grupo114.tarifas.model.DetalleTarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DetalleTarifaRepository extends JpaRepository<DetalleTarifa, Long> {

    // Busca el primer detalle para una tarifa ACTIVA por concepto
    Optional<DetalleTarifa> findFirstByTarifa_ActivaTrueAndConcepto(String concepto);
}
