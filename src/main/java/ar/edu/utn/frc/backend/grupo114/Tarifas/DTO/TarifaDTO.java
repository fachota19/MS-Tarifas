package ar.edu.utn.frc.backend.grupo114.Tarifas.DTO;

import lombok.Data;
import java.time.LocalDate;

@Data // (De Lombok, para getters/setters)
public class TarifaDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private boolean activa;
    // No incluimos los 'detalles' aquí para mantener simple la lista
}