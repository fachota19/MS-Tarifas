package ar.edu.utn.frc.backend.grupo114.tarifas.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tarifas")
@Getter
@Setter
@NoArgsConstructor
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    private String descripcion;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(nullable = false)
    private boolean activa;

    // 🔥 RELACIÓN CORRECTA ONE-TO-MANY
    @OneToMany(mappedBy = "tarifa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleTarifa> detalles = new ArrayList<>();

    // Método helper opcional
    public void agregarDetalle(DetalleTarifa detalle) {
        detalle.setTarifa(this);
        this.detalles.add(detalle);
    }
}
