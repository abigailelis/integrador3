package tudai.integrador3.service.dto.carrera.carreraRequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties( ignoreUnknown = true)
public class CarreraRequestDTO {

    @NotBlank( message = "El nombre de la carrera es un campo obligatorio.")
    private String carrera;

    @NotNull( message = "La duración de la carrera es un campo obligatorio.")
    private int duracion;

    @NotNull( message = "La cantidad de inscriptos de la carrera es un campo obligatorio.")
    private long inscriptos;
}
