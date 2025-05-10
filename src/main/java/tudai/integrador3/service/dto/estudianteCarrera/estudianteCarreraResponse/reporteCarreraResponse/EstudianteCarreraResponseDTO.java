package tudai.integrador3.service.dto.estudianteCarrera.estudianteCarreraResponse.reporteCarreraResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EstudianteCarreraResponseDTO {

    private int inscripcion;
    private int graduacion;
    private int antiguedad;
    private int dni_estudiante;
    private String nombre_carrera;

}
