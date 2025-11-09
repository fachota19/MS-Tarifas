package ar.edu.utn.frc.backend.grupo114.Tarifas.DTO;

import ar.edu.utn.frc.backend.grupo114.Tarifas.DTO.DetalleTarifaDTO;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class TarifaConDetallesDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private boolean activa;

    // La lista de detalles asociados
    private List<DetalleTarifaDTO> detalles;
}