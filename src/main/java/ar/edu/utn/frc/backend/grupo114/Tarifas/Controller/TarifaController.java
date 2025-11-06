import com.tpi.tarifas.dto.CrearTarifaRequestDTO;
import com.tpi.tarifas.dto.TarifaDTO;
import com.tpi.tarifas.service.TarifaService;
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
}