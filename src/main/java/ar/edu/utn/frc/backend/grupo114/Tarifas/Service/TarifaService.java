package ar.edu.utn.frc.backend.grupo114.Tarifas.Service;

// --- Imports de DTOs ---
import ar.edu.utn.frc.backend.grupo114.Tarifas.DTO.CalculoRequestDTO;
import ar.edu.utn.frc.backend.grupo114.Tarifas.DTO.CalculoResponseDTO;
import ar.edu.utn.frc.backend.grupo114.Tarifas.DTO.CrearTarifaRequestDTO;
import ar.edu.utn.frc.backend.grupo114.Tarifas.DTO.DetalleTarifaDTO;
import ar.edu.utn.frc.backend.grupo114.Tarifas.DTO.TarifaConDetallesDTO;
import ar.edu.utn.frc.backend.grupo114.Tarifas.DTO.TarifaDTO;
// --- Imports de Modelos ---
import ar.edu.utn.frc.backend.grupo114.Model.DetalleTarifa;
import ar.edu.utn.frc.backend.grupo114.Model.Tarifa;
// --- Imports de Repositorio y Excepción ---
import ar.edu.utn.frc.backend.grupo114.Tarifas.Repository.DetalleTarifaRepository;
import ar.edu.utn.frc.backend.grupo114.Tarifas.Repository.TarifaRepository;
import ar.edu.utn.frc.backend.grupo114.Tarifas.Exception.ResourceNotFound;

// --- Imports de Spring y Java ---
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate; // Para futuras llamadas a otros MS

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TarifaService {

    private final TarifaRepository tarifaRepository;
    private final DetalleTarifaRepository detalleTarifaRepository;
    // private final RestTemplate restTemplate; // Lo necesitarás para llamar a otros MS

    // Constantes para el cálculo estimado
    private static final double VELOCIDAD_PROMEDIO_KMH = 80.0;

    public TarifaService(TarifaRepository tarifaRepository,
                         DetalleTarifaRepository detalleTarifaRepository
            /* RestTemplate restTemplate */) {
        this.tarifaRepository = tarifaRepository;
        this.detalleTarifaRepository = detalleTarifaRepository;
        // this.restTemplate = restTemplate;
    }

    // Lógica para GET /tarifas
    @Transactional(readOnly = true)
    public List<TarifaDTO> getTodasLasTarifas() {
        List<Tarifa> tarifas = tarifaRepository.findAll();
        return tarifas.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // --- MÉTODO ACTUALIZADO ---
    // Lógica para POST /tarifas (Ahora guarda detalles)
    @Transactional
    public TarifaDTO crearTarifa(CrearTarifaRequestDTO requestDTO) {
        // 1. Convierte el DTO principal a una Entidad
        Tarifa tarifa = new Tarifa();
        tarifa.setNombre(requestDTO.getNombre());
        tarifa.setDescripcion(requestDTO.getDescripcion());
        tarifa.setFechaInicio(requestDTO.getFechaInicio());
        tarifa.setFechaFin(requestDTO.getFechaFin());
        tarifa.setActiva(requestDTO.isActiva());
        tarifa.setDetalles(new ArrayList<>()); // Inicializa la lista

        // 2. Guarda la tarifa principal PRIMERO (para obtener un ID)
        Tarifa tarifaGuardada = tarifaRepository.save(tarifa);

        // 3. Itera sobre los DTOs de detalles
        if (requestDTO.getDetalles() != null) {
            for (var detalleDTO : requestDTO.getDetalles()) {
                DetalleTarifa detalle = new DetalleTarifa();
                detalle.setConcepto(detalleDTO.getConcepto());
                detalle.setValor(detalleDTO.getValor());
                detalle.setUnidad(detalleDTO.getUnidad());

                // 4. Establece la relación: vincula el detalle a la tarifa guardada
                detalle.setTarifa(tarifaGuardada);

                // 5. Agrega el detalle a la lista de la tarifa (opcional si guardas por separado)
                tarifaGuardada.getDetalles().add(detalle);
            }
            // 6. Guarda todos los detalles
            // (Gracias a CascadeType.ALL en la Entidad Tarifa,
            // con solo guardar la tarifa de nuevo (o al final de la transacción)
            // se guardarían los detalles. Pero guardarlos explícitamente es más claro).
            detalleTarifaRepository.saveAll(tarifaGuardada.getDetalles());
        }

        // 7. Convierte la entidad guardada (con ID) a un DTO y la devuelve
        return convertirADTO(tarifaGuardada);
    }

    // Lógica para GET /tarifas/{id}/detalles
    @Transactional(readOnly = true)
    public TarifaConDetallesDTO getTarifaConDetalles(Long id) {
        Tarifa tarifa = tarifaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("No se encontró la tarifa con ID: " + id));
        return convertirADTOConDetalles(tarifa);
    }

    // --- MÉTODO NUEVO ---
    // Lógica para POST /tarifas/calcular
    @Transactional(readOnly = true)
    public CalculoResponseDTO calcularCostoEstimado(CalculoRequestDTO request) {

        // 1. OBTENER DISTANCIA (Simulación)
        // Aquí deberías llamar a la API de Google Maps
        // Ejemplo: double distanciaKm = restTemplate.getForObject("http://google-maps-api/...", Double.class);
        double distanciaKm = getDistanciaEstimada(
                request.getLatitudOrigen(), request.getLongitudOrigen(),
                request.getLatitudDestino(), request.getLongitudDestino()
        );

        // 2. OBTENER TIEMPO ESTIMADO
        // "El tiempo estimado se calcula en base a las distancias"
        double tiempoEstimadoHoras = distanciaKm / VELOCIDAD_PROMEDIO_KMH;

        // 3. OBTENER VALORES DE TARIFA BASE
        // Buscamos los valores de la tarifa activa.
        // Asumimos que hay una tarifa "base" o "aproximada" activa.
        BigDecimal costoKmBase = getValorTarifa("COSTO_KM_BASE");
        BigDecimal valorCombustible = getValorTarifa("VALOR_COMBUSTIBLE");
        BigDecimal consumoPromedio = getValorTarifa("CONSUMO_PROMEDIO_GENERAL"); // [cite: 65]
        BigDecimal costoEstadiaDiario = getValorTarifa("COSTO_ESTADIA_DIARIO"); // [cite: 66]

        // 4. CALCULAR COSTOS
        //
        BigDecimal costoDistancia = costoKmBase.multiply(BigDecimal.valueOf(distanciaKm));

        BigDecimal costoCombustible = BigDecimal.ZERO;
        if (consumoPromedio.compareTo(BigDecimal.ZERO) > 0) {
            costoCombustible = (BigDecimal.valueOf(distanciaKm).divide(consumoPromedio, 2, RoundingMode.HALF_UP))
                    .multiply(valorCombustible);
        }

        BigDecimal costoEstadia = costoEstadiaDiario.multiply(BigDecimal.valueOf(request.getDiasEstadiaEstimados()));

        // "La tarifa aproximada del envío en base a valores promedio"
        // Nota: La regla de "Cargos de Gestión" [cite: 103] parece ser para el costo FINAL,
        // así que la omitimos en el APROXIMADO.
        BigDecimal costoTotal = costoDistancia.add(costoCombustible).add(costoEstadia);

        // 5. DEVOLVER RESPUESTA
        return new CalculoResponseDTO(
                costoTotal.setScale(2, RoundingMode.HALF_UP),
                tiempoEstimadoHoras,
                "ARS"
        );
    }

    // --- Métodos Privados de Ayuda ---

    /**
     * Busca un valor de tarifa activa por su concepto.
     * TODO: Esto debe mejorarse para buscar la tarifa activa correcta por fecha.
     */
    private BigDecimal getValorTarifa(String concepto) {
        // Esta lógica es simplificada. Debería buscar la tarifa activa (activa=true y por fecha)
        // que contenga ese concepto.
        return detalleTarifaRepository.findByConcepto(concepto)
                .map(DetalleTarifa::getValor)
                .orElse(BigDecimal.ZERO); // Devuelve 0 si no encuentra el concepto
    }

    /**
     * SIMULACIÓN de llamada a API externa (Google Maps)
     */
    private double getDistanciaEstimada(BigDecimal latO, BigDecimal lonO, BigDecimal latD, BigDecimal lonD) {
        // Lógica de simulación: devuelve un valor fijo
        // En la implementación real, aquí se usaría RestTemplate o WebClient
        // para llamar a la API de Google Maps Directions.
        return 450.5; // Distancia fija en KM para probar
    }

    // --- Métodos de Mapeo (Conversión) ---

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

    private TarifaConDetallesDTO convertirADTOConDetalles(Tarifa tarifa) {
        TarifaConDetallesDTO dto = new TarifaConDetallesDTO();
        dto.setId(tarifa.getId());
        dto.setNombre(tarifa.getNombre());
        dto.setDescripcion(tarifa.getDescripcion());
        dto.setFechaInicio(tarifa.getFechaInicio());
        dto.setFechaFin(tarifa.getFechaFin());
        dto.setActiva(tarifa.isActiva());

        List<DetalleTarifaDTO> detallesDTO = tarifa.getDetalles().stream()
                .map(this::convertirDetalleADTO)
                .collect(Collectors.toList());

        dto.setDetalles(detallesDTO);
        return dto;
    }

    private DetalleTarifaDTO convertirDetalleADTO(DetalleTarifa detalle) {
        DetalleTarifaDTO dto = new DetalleTarifaDTO();
        dto.setId(detalle.getId());
        dto.setConcepto(detalle.getConcepto());
        dto.setValor(detalle.getValor());
        dto.setUnidad(detalle.getUnidad());
        return dto;
    }
}