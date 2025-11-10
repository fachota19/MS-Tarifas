package ar.edu.utn.frc.backend.grupo114.Tarifas.Repository;

import ar.edu.utn.frc.backend.grupo114.Model.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Long> {


}