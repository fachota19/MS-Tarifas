package ar.edu.utn.frc.backend.grupo114.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "detalles_tarifa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleTarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tarifa_id", nullable = false)
    private Tarifa tarifa;

    @Column(nullable = false)
    private String concepto;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column
    private String unidad;
}
