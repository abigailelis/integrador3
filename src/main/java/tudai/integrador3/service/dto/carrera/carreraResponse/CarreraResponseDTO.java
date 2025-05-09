package tudai.integrador3.service.dto.carrera.carreraResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import tudai.integrador3.domain.Carrera;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CarreraResponseDTO {

    private String carrera;
    private int duracion;
    private long inscriptos;
    
    public CarreraResponseDTO(CarreraResponseDTO carrera) {
        this.carrera = carrera.getCarrera();
        this.duracion = carrera.getDuracion();
        this.inscriptos = carrera.getInscriptos();
    }

}
