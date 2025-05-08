package tudai.integrador3.service.dto.estudianteCarrera.estudianteCarreraRequest.reporteCarreraRequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties( ignoreUnknown = true)
public class EstudianteCarreraRequestDTO {

    @NotNull( message = "El año de inscripción es un campo obligatorio.")
    private int inscripcion;

    @NotNull( message = "El año de graduación es un campo obligatorio.")
    private int graduacion;

    @NotNull( message = "La antiguedad es un campo obligatorio.")
    private int antiguedad;

    @NotNull( message = "El estudiante es un campo obligatorio.")
    private int dni_estudiante;

    @NotBlank( message = "La carrera es un campo obligatorio.")
    private String nombre_carrera;

}
