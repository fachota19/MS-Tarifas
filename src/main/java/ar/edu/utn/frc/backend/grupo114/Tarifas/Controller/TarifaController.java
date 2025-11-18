package ar.edu.utn.frc.backend.grupo114.tarifas.controller;

import ar.edu.utn.frc.backend.grupo114.tarifas.dto.*;
import ar.edu.utn.frc.backend.grupo114.tarifas.service.TarifaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarifas")
public class TarifaController {

    private final TarifaService tarifaService;

    public TarifaController(TarifaService tarifaService) {
        this.tarifaService = tarifaService;
    }

    // ────────────────────────────────────────────────────────────────
    // LISTAR TODAS
    @GetMapping
    public ResponseEntity<List<TarifaDTO>> listarTarifas() {
        return ResponseEntity.ok(tarifaService.getTodasLasTarifas());
    }

    // ────────────────────────────────────────────────────────────────
    // OBTENER POR ID
    @GetMapping("/{id}")
    public ResponseEntity<TarifaConDetallesDTO> obtenerTarifaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tarifaService.getTarifaConDetalles(id));
    }

    // ────────────────────────────────────────────────────────────────
    // CREAR TARIFA
    @PostMapping
    public ResponseEntity<TarifaConDetallesDTO> crearTarifa(@RequestBody CrearTarifaRequestDTO request) {
        return new ResponseEntity<>(tarifaService.crearTarifa(request), HttpStatus.CREATED);
    }

    // ────────────────────────────────────────────────────────────────
    // CREAR DETALLE
    @PostMapping("/{id}/detalles")
    public ResponseEntity<DetalleTarifaDTO> crearDetalle(
            @PathVariable Long id,
            @RequestBody CrearDetalleTarifaDTO request
    ) {
        return new ResponseEntity<>(tarifaService.agregarDetalle(id, request), HttpStatus.CREATED);
    }

    // ────────────────────────────────────────────────────────────────
    // ACTIVAR TARIFA
    @PutMapping("/{id}/activar")
    public ResponseEntity<TarifaDTO> activarTarifa(@PathVariable Long id) {
        return ResponseEntity.ok(tarifaService.activarTarifa(id));
    }

    // ────────────────────────────────────────────────────────────────
    // DESACTIVAR TARIFA
    @PutMapping("/{id}/desactivar")
    public ResponseEntity<TarifaDTO> desactivarTarifa(@PathVariable Long id) {
        return ResponseEntity.ok(tarifaService.desactivarTarifa(id));
    }

    // ────────────────────────────────────────────────────────────────
    // ELIMINAR TARIFA
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTarifa(@PathVariable Long id) {
        tarifaService.eliminarTarifa(id);
        return ResponseEntity.noContent().build();
    }

    // ────────────────────────────────────────────────────────────────
    // ELIMINAR DETALLE
    @DeleteMapping("/{id}/detalles/{detalleId}")
    public ResponseEntity<Void> eliminarDetalle(
            @PathVariable Long id,
            @PathVariable Long detalleId
    ) {
        tarifaService.eliminarDetalle(id, detalleId);
        return ResponseEntity.noContent().build();
    }

    // ────────────────────────────────────────────────────────────────
    // CALCULAR COSTO ESTIMADO
    @PostMapping("/calcular")
    public ResponseEntity<CalculoResponseDTO> calcularTarifa(@RequestBody CalculoRequestDTO requestDTO) {
        return ResponseEntity.ok(tarifaService.calcularCostoEstimado(requestDTO));
    }
}
