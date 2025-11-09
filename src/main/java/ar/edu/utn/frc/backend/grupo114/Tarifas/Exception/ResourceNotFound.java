package ar.edu.utn.frc.backend.grupo114.Tarifas.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Esta anotación le dice a Spring que cuando esta excepción sea lanzada,
// debe responder automáticamente con un código HTTP 404.
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFound extends RuntimeException {

    public ResourceNotFound(String message) {
        super(message);
    }
}