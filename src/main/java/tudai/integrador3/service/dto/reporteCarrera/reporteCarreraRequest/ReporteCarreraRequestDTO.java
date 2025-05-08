package tudai.integrador3.service.dto.reporteCarrera.reporteCarreraRequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonIgnoreProperties( ignoreUnknown = true)
public class ReporteCarreraRequestDTO {

    @NotBlank( message = "El nombre de la carrera es un campo obligatorio.")
    private String carrera;

    @NotBlank( message = "El año es un campo obligatorio.")
    private int anio;

    @NotBlank( message = "La cantidad de inscriptos es un campo obligatorio.")
    private long inscriptos;

    @NotBlank( message = "La cantidad de graduados es un campo obligatorio.")
    private long graduados;


}
