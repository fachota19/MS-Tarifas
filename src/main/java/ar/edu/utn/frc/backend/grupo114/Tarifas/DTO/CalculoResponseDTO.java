package ar.edu.utn.frc.backend.grupo114.tarifas.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculoResponseDTO {

    private Long tarifaId;
    private BigDecimal total;
}
