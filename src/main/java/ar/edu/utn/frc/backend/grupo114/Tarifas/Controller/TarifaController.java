// Paquete corregido a tu estructura
package ar.edu.utn.frc.backend.grupo114.Tarifas.Controller;

// --- Imports de DTOs ---
// (Desde ...Tarifas.DTO)
import ar.edu.utn.frc.backend.grupo114.Tarifas.DTO.CrearTarifaRequestDTO;
import ar.edu.utn.frc.backend.grupo114.Tarifas.DTO.TarifaDTO;
import ar.edu.utn.frc.backend.grupo114.Tarifas.DTO.TarifaConDetallesDTO;
import ar.edu.utn.frc.backend.grupo114.Tarifas.DTO.CalculoRequestDTO;
import ar.edu.utn.frc.backend.grupo114.Tarifas.DTO.CalculoResponseDTO;

// --- Import del Servicio ---
// (Desde ...Tarifas.Service)
import ar.edu.utn.frc.backend.grupo114.Tarifas.Service.TarifaService;

// --- Imports de Spring y Java ---
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarifas") // URL base para este controlador
public class TarifaController {

    private final TarifaService tarifaService;

    // Inyección de dependencias (el servicio)
    public TarifaController(TarifaService tarifaService) {
        this.tarifaService = tarifaService;
    }

    /**
     * Endpoint para GET /tarifas
     * Lista todas las tarifas activas.
     */
    @GetMapping
    public ResponseEntity<List<TarifaDTO>> getTarifas() {
        List<TarifaDTO> tarifas = tarifaService.getTodasLasTarifas();
        return ResponseEntity.ok(tarifas); // Devuelve 200 OK
    }

    /**
     * Endpoint para POST /tarifas
     * Crea una nueva tarifa.
     */
    @PostMapping
    public ResponseEntity<TarifaDTO> crearTarifa(@RequestBody CrearTarifaRequestDTO requestDTO) {
        TarifaDTO nuevaTarifa = tarifaService.crearTarifa(requestDTO);
        // Devuelve 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaTarifa);
    }

    /**
     * Endpoint para GET /tarifas/{id}/detalles
     * Muestra los conceptos de cálculo asociados.
     */
    @GetMapping("/{id}/detalles")
    public ResponseEntity<TarifaConDetallesDTO> getTarifaConDetalles(@PathVariable Long id) {
        TarifaConDetallesDTO tarifa = tarifaService.getTarifaConDetalles(id);
        return ResponseEntity.ok(tarifa); // Devuelve 200 OK
    }

    /**
     * Endpoint para POST /tarifas/calcular
     * Calcula el costo estimado del traslado.
     */
    @PostMapping("/calcular")
    public ResponseEntity<CalculoResponseDTO> calcularTarifa(@RequestBody CalculoRequestDTO requestDTO) {

        // Llama al servicio para hacer el cálculo
        CalculoResponseDTO respuesta = tarifaService.calcularCostoEstimado(requestDTO);

        // Devuelve la respuesta con 200 OK
        return ResponseEntity.ok(respuesta);
    }
}