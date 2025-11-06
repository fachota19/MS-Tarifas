package ar.edu.utn.frc.backend.grupo114.Tarifas.DTO;

import lombok.Data;
import java.time.LocalDate;
// Podríamos añadir validaciones (ej. @NotNull) aquí más adelante
@Data
public class CrearTarifaRequestDTO {
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private boolean activa;
    // Aquí podrías añadir también una lista de DTOs para los 'detalles'
}