package ar.edu.utn.frc.backend.grupo114.tarifas.service;

import ar.edu.utn.frc.backend.grupo114.tarifas.dto.*;
import ar.edu.utn.frc.backend.grupo114.tarifas.exception.ResourceNotFoundException;
import ar.edu.utn.frc.backend.grupo114.tarifas.model.Concepto;
import ar.edu.utn.frc.backend.grupo114.tarifas.model.DetalleTarifa;
import ar.edu.utn.frc.backend.grupo114.tarifas.model.Tarifa;
import ar.edu.utn.frc.backend.grupo114.tarifas.repository.DetalleTarifaRepository;
import ar.edu.utn.frc.backend.grupo114.tarifas.repository.TarifaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TarifaService {

    private final TarifaRepository tarifaRepository;
    private final DetalleTarifaRepository detalleTarifaRepository;

    public TarifaService(TarifaRepository tarifaRepository,
                         DetalleTarifaRepository detalleTarifaRepository) {
        this.tarifaRepository = tarifaRepository;
        this.detalleTarifaRepository = detalleTarifaRepository;
    }

    // ============================================================
    // LISTAR TARIFAS
    // ============================================================
    public List<TarifaDTO> getTodasLasTarifas() {
        return tarifaRepository.findAll()
                .stream()
                .map(this::mapToTarifaDTO)
                .collect(Collectors.toList());
    }

    // ============================================================
    // OBTENER TARIFA CON DETALLES
    // ============================================================
    public TarifaConDetallesDTO getTarifaConDetalles(Long id) {
        Tarifa tarifa = tarifaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarifa no encontrada con id: " + id));

        List<DetalleTarifaDTO> detallesDTO = tarifa.getDetalles()
                .stream()
                .map(this::mapToDetalleDTO)
                .collect(Collectors.toList());

        return new TarifaConDetallesDTO(
                tarifa.getId(),
                tarifa.getNombre(),
                tarifa.getDescripcion(),
                tarifa.getFechaInicio(),
                tarifa.getFechaFin(),
                tarifa.isActiva(),
                detallesDTO
        );
    }

    // ============================================================
    // CREAR TARIFA
    // ============================================================
    public TarifaConDetallesDTO crearTarifa(CrearTarifaRequestDTO request) {

        Tarifa tarifa = new Tarifa();
        tarifa.setNombre(request.getNombre());
        tarifa.setDescripcion(request.getDescripcion());
        tarifa.setFechaInicio(request.getFechaInicio());
        tarifa.setFechaFin(request.getFechaFin());
        tarifa.setActiva(request.isActiva());

        tarifaRepository.save(tarifa);

        if (request.getDetalles() != null) {
            for (CrearDetalleTarifaDTO d : request.getDetalles()) {

                DetalleTarifa det = new DetalleTarifa();
                det.setTarifa(tarifa);
                det.setValor(d.getValor());
                det.setUnidad(d.getUnidad());

                // ❗ Conversión String → Enum
                det.setConcepto(
                        Concepto.valueOf(d.getConcepto().toUpperCase())
                );

                detalleTarifaRepository.save(det);
            }
        }

        return getTarifaConDetalles(tarifa.getId());
    }

    // ============================================================
    // AGREGAR DETALLE A TARIFA EXISTENTE
    // ============================================================
    public DetalleTarifaDTO agregarDetalle(Long tarifaId, CrearDetalleTarifaDTO req) {

        Tarifa tarifa = tarifaRepository.findById(tarifaId)
                .orElseThrow(() -> new ResourceNotFoundException("Tarifa no encontrada con id: " + tarifaId));

        DetalleTarifa detalle = new DetalleTarifa();
        detalle.setTarifa(tarifa);
        detalle.setUnidad(req.getUnidad());
        detalle.setValor(req.getValor());
        detalle.setConcepto(Concepto.valueOf(req.getConcepto().toUpperCase()));

        detalleTarifaRepository.save(detalle);

        return mapToDetalleDTO(detalle);
    }

    // ============================================================
    // ACTIVAR TARIFA
    // ============================================================
    public TarifaDTO activarTarifa(Long id) {
        Tarifa tarifa = tarifaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarifa no encontrada con id: " + id));

        tarifa.setActiva(true);
        tarifaRepository.save(tarifa);

        return mapToTarifaDTO(tarifa);
    }

    // ============================================================
    // DESACTIVAR TARIFA
    // ============================================================
    public TarifaDTO desactivarTarifa(Long id) {
        Tarifa tarifa = tarifaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarifa no encontrada con id: " + id));

        tarifa.setActiva(false);
        tarifaRepository.save(tarifa);

        return mapToTarifaDTO(tarifa);
    }

    // ============================================================
    // ELIMINAR TARIFA
    // ============================================================
    public void eliminarTarifa(Long id) {
        Tarifa tarifa = tarifaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarifa no encontrada con id: " + id));

        detalleTarifaRepository.deleteAll(tarifa.getDetalles());
        tarifaRepository.delete(tarifa);
    }

    // ============================================================
    // ELIMINAR DETALLE
    // ============================================================
    public void eliminarDetalle(Long tarifaId, Long detalleId) {

        Tarifa tarifa = tarifaRepository.findById(tarifaId)
                .orElseThrow(() -> new ResourceNotFoundException("Tarifa no encontrada con id: " + tarifaId));

        DetalleTarifa detalle = detalleTarifaRepository.findById(detalleId)
                .orElseThrow(() -> new ResourceNotFoundException("Detalle no encontrado con id: " + detalleId));

        if (!detalle.getTarifa().getId().equals(tarifaId)) {
            throw new ResourceNotFoundException("El detalle no pertenece a la tarifa");
        }

        detalleTarifaRepository.delete(detalle);
    }

    // ============================================================
    // CALCULAR COSTO ESTIMADO
    // ============================================================
    public CalculoResponseDTO calcularCostoEstimado(CalculoRequestDTO req) {

        Tarifa tarifa = tarifaRepository.findById(req.getTarifaId())
                .orElseThrow(() -> new ResourceNotFoundException("Tarifa no encontrada con id: " + req.getTarifaId()));

        BigDecimal total = tarifa.getDetalles()
                .stream()
                .map(DetalleTarifa::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .multiply(BigDecimal.valueOf(req.getCantidad()));

        return new CalculoResponseDTO(req.getTarifaId(), total);
    }

    // ============================================================
    // MAPPERS
    // ============================================================
    private TarifaDTO mapToTarifaDTO(Tarifa t) {
        return new TarifaDTO(
                t.getId(),
                t.getNombre(),
                t.getDescripcion(),
                t.getFechaInicio(),
                t.getFechaFin(),
                t.isActiva()
        );
    }

    private DetalleTarifaDTO mapToDetalleDTO(DetalleTarifa d) {
        return new DetalleTarifaDTO(
                d.getId(),
                d.getConcepto().name(),  // Enum → String
                d.getUnidad(),
                d.getValor()
        );
    }
}
