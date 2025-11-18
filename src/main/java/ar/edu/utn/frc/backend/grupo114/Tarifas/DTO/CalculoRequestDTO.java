package ar.edu.utn.frc.backend.grupo114.tarifas.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculoRequestDTO {

    private Long tarifaId;
    private Double cantidad; 
}
