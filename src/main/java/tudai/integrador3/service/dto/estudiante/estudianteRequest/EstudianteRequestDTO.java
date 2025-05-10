package tudai.integrador3.service.dto.estudiante.estudianteRequest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonIgnoreProperties( ignoreUnknown = true)
@AllArgsConstructor
public class EstudianteRequestDTO {

    @NotNull( message = "El DNI es un campo obligatorio.")
    private int DNI;

    @NotBlank( message = "El nombre es un campo obligatorio.")
    private String nombre;

    @NotBlank( message = "El apellido es un campo obligatorio.")
    private String apellido;

    @NotNull( message = "La edad es un campo obligatorio.")
    private int edad;

    @NotBlank( message = "El genero es un campo obligatorio.")
    private String genero;

    @NotBlank( message = "La ciudad es un campo obligatorio.")
    private String ciudad;

    @NotNull( message = "El número de LU es un campo obligatorio.")
    private int LU;
}
