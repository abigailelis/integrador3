package tudai.integrador3.service.dto.estudianteCarrera.estudianteCarreraRequest.reporteCarreraRequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonIgnoreProperties( ignoreUnknown = true)
@AllArgsConstructor
public class EstudianteCarreraRequestDTO {

    private int inscripcion;

    private int graduacion;

    private int antiguedad;

    @NotNull( message = "El DNI del estudiante es un campo obligatorio.")
    private int dni_estudiante;

    @NotNull( message = "El id la carrera es un campo obligatorio.")
    private int id_carrera;

}
