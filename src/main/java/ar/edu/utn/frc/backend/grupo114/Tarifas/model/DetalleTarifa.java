package ar.edu.utn.frc.backend.grupo114.tarifas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "detalles_tarifa")
@Getter
@Setter
@NoArgsConstructor
public class DetalleTarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String concepto;

    @Column(length = 50)
    private String unidad;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valor;

    // 🔥 ManyToOne bien configurado
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tarifa_id", nullable = false)
    private Tarifa tarifa;
}
