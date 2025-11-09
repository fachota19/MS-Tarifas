package ar.edu.utn.frc.backend.grupo114.Tarifas.DTO;

import lombok.Data;
import java.math.BigDecimal;

@Data // (De Lombok, para getters/setters)
public class CalculoResponseDTO {

    private BigDecimal costoEstimado;
    private Double tiempoEstimadoHoras;
    private String moneda; // (Buena práctica incluir la moneda)

    // Constructor simple por si lo necesitamos
    public CalculoResponseDTO(BigDecimal costoEstimado, Double tiempoEstimadoHoras, String moneda) {
        this.costoEstimado = costoEstimado;
        this.tiempoEstimadoHoras = tiempoEstimadoHoras;
        this.moneda = moneda;
    }
}