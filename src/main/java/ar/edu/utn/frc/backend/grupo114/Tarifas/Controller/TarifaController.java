package ar.edu.utn.frc.backend.grupo114.tarifas.controller;

import ar.edu.utn.frc.backend.grupo114.tarifas.dto.*;
import ar.edu.utn.frc.backend.grupo114.tarifas.service.TarifaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarifas")
public class TarifaController {

    private final TarifaService tarifaService;

    public TarifaController(TarifaService tarifaService) {
        this.tarifaService = tarifaService;
    }

    // =============================
    // LISTAR TODAS LAS TARIFAS
    // =============================
    @GetMapping
    public List<TarifaDTO> listar() {
        return tarifaService.getTodasLasTarifas();
    }

    // =============================
    // OBTENER TARIFA + DETALLES
    // =============================
    @GetMapping("/{id}")
    public TarifaConDetallesDTO obtener(@PathVariable Long id) {
        return tarifaService.getTarifaConDetalles(id);
    }

    // =============================
    // CREAR TARIFA
    // =============================
    @PostMapping
    public ResponseEntity<TarifaConDetallesDTO> crear(@RequestBody CrearTarifaRequestDTO req) {
        return ResponseEntity.ok(tarifaService.crearTarifa(req));
    }

    // =============================
    // AGREGAR DETALLE A TARIFA
    // =============================
    @PostMapping("/{id}/detalles")
    public ResponseEntity<DetalleTarifaDTO> agregarDetalle(
            @PathVariable Long id,
            @RequestBody CrearDetalleTarifaDTO req) {

        return ResponseEntity.ok(tarifaService.agregarDetalle(id, req));
    }

    // =============================
    // ACTIVAR TARIFA
    // =============================
    @PutMapping("/{id}/activar")
    public ResponseEntity<TarifaDTO> activar(@PathVariable Long id) {
        return ResponseEntity.ok(tarifaService.activarTarifa(id));
    }

    // =============================
    // DESACTIVAR TARIFA
    // =============================
    @PutMapping("/{id}/desactivar")
    public ResponseEntity<TarifaDTO> desactivar(@PathVariable Long id) {
        return ResponseEntity.ok(tarifaService.desactivarTarifa(id));
    }

    // =============================
    // ELIMINAR TARIFA
    // =============================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tarifaService.eliminarTarifa(id);
        return ResponseEntity.noContent().build();
    }

    // =============================
    // CALCULAR COSTO ESTIMADO
    // =============================
    @PostMapping("/calcular")
    public ResponseEntity<CalculoResponseDTO> calcular(@RequestBody CalculoRequestDTO req) {
        return ResponseEntity.ok(tarifaService.calcularCostoEstimado(req));
    }
}
