package ar.edu.utn.frc.backend.grupo114.Tarifas.Service;

import com.tpi.tarifas.dto.CrearTarifaRequestDTO;
import com.tpi.tarifas.dto.TarifaDTO;
import com.tpi.tarifas.model.Tarifa;
import com.tpi.tarifas.repository.TarifaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TarifaService {

    private final TarifaRepository tarifaRepository;

    // Inyección de dependencias (el repositorio)
    public TarifaService(TarifaRepository tarifaRepository) {
        this.tarifaRepository = tarifaRepository;
    }

    // Lógica para GET /tarifas
    @Transactional(readOnly = true)
    public List<TarifaDTO> getTodasLasTarifas() {
        // 1. Busca todas las entidades Tarifa
        List<Tarifa> tarifas = tarifaRepository.findAll();

        // 2. Convierte la lista de Entidades a una lista de DTOs
        return tarifas.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Lógica para POST /tarifas
    @Transactional
    public TarifaDTO crearTarifa(CrearTarifaRequestDTO requestDTO) {
        // 1. Convierte el DTO de request a una Entidad
        Tarifa tarifa = new Tarifa();
        tarifa.setNombre(requestDTO.getNombre());
        tarifa.setDescripcion(requestDTO.getDescripcion());
        tarifa.setFechaInicio(requestDTO.getFechaInicio());
        tarifa.setFechaFin(requestDTO.getFechaFin());
        tarifa.setActiva(requestDTO.isActiva());

        // (Aquí iría la lógica para convertir y añadir los 'detalles' si vinieran en el DTO)

        // 2. Guarda la nueva entidad en la BD
        Tarifa tarifaGuardada = tarifaRepository.save(tarifa);

        // 3. Convierte la entidad guardada (que ya tiene ID) a un DTO y la devuelve
        return convertirADTO(tarifaGuardada);
    }

    // Método privado para mapear Entidad -> DTO
    private TarifaDTO convertirADTO(Tarifa tarifa) {
        TarifaDTO dto = new TarifaDTO();
        dto.setId(tarifa.getId());
        dto.setNombre(tarifa.getNombre());
        dto.setDescripcion(tarifa.getDescripcion());
        dto.setFechaInicio(tarifa.getFechaInicio());
        dto.setFechaFin(tarifa.getFechaFin());
        dto.setActiva(tarifa.isActiva());
        return dto;
    }
}