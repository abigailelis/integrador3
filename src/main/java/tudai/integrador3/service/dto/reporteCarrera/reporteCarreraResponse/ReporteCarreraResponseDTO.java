package tudai.integrador3.service.dto.reporteCarrera.reporteCarreraResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReporteCarreraResponseDTO {

    private String carrera;
    private int anio;
    private long inscriptos;
    private long graduados;
}
