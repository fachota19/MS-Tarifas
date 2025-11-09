package ar.edu.utn.frc.backend.grupo114.Tarifas.DTO;

import lombok.Data;
import java.math.BigDecimal;

@Data // (De Lombok, para getters/setters)
public class CalculoRequestDTO {

    // Necesitamos las coordenadas para la API de Google Maps
    private BigDecimal latitudOrigen;
    private BigDecimal longitudOrigen;
    private BigDecimal latitudDestino;
    private BigDecimal longitudDestino;

    // Necesitamos esto para filtrar camiones y calcular tarifas base
    private BigDecimal peso;
    private BigDecimal volumen;

    // Necesario para el cálculo de estadía en depósitos
    private Integer diasEstadiaEstimados;
}