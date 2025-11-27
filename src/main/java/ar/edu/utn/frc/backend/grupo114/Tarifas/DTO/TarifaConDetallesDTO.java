package ar.edu.utn.frc.backend.grupo114.tarifas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
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
