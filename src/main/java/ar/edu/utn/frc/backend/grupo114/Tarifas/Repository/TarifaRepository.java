package ar.edu.utn.frc.backend.grupo114.tarifas.repository;

import ar.edu.utn.frc.backend.grupo114.tarifas.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Long> {
    // Si más adelante necesitás buscar por activa/fecha, se agregan aquí
}
