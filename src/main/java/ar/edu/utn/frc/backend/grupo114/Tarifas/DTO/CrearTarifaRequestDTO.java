package ar.edu.utn.frc.backend.grupo114.tarifas.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CrearTarifaRequestDTO {

    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private boolean activa;

    private List<CrearDetalleTarifaDTO> detalles;

}
