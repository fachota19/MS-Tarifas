package ar.edu.utn.frc.backend.grupo114.tarifas.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CrearDetalleTarifaDTO {

    private String concepto;
    private BigDecimal valor;
    private String unidad;

}
