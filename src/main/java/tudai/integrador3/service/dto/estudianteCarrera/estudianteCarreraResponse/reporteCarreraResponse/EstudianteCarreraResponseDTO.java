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

    public EstudianteCarreraResponseDTO(EstudianteCarreraResponseDTO rcResponseDTO) {
        this.inscripcion = rcResponseDTO.getInscripcion();
        this.graduacion = rcResponseDTO.getGraduacion();
        this.antiguedad = rcResponseDTO.getAntiguedad();
        this.dni_estudiante = rcResponseDTO.getDni_estudiante();
        this.nombre_carrera = rcResponseDTO.getNombre_carrera();
    }

}
