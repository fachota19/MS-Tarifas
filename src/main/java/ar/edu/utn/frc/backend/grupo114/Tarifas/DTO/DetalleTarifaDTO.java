package ar.edu.utn.frc.backend.grupo114.Tarifas.DTO;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DetalleTarifaDTO {
    private Long id;
    private String concepto;
    private BigDecimal valor;
    private String unidad;
}