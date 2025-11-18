package ar.edu.utn.frc.backend.grupo114.tarifas.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TarifaConDetallesDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private boolean activa;

    private List<DetalleTarifaDTO> detalles;
}
