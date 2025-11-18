package ar.edu.utn.frc.backend.grupo114.tarifas.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleTarifaDTO {

    private Long id;
    private String concepto;
    private String unidad;
    private BigDecimal valor;
}
