package tudai.integrador3.service.dto.estudiante.estudianteResponse;

import tudai.integrador3.domain.Estudiante;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class EstudianteResponseDTO {

    private int DNI;
    private String nombre;
    private String apellido;
    private int edad;
    private String genero;
    private String ciudad;
    private int LU;

}
