package tudai.integrador3.service;

import org.springframework.beans.factory.annotation.Autowired;
import tudai.integrador3.domain.Carrera;
import tudai.integrador3.domain.Estudiante;
import tudai.integrador3.repository.CarreraRepository;
import tudai.integrador3.service.dto.carrera.carreraRequest.CarreraRequestDTO;
import tudai.integrador3.service.dto.carrera.carreraResponse.CarreraResponseDTO;
import tudai.integrador3.service.dto.estudiante.estudianteResponse.EstudianteResponseDTO;
import tudai.integrador3.service.dto.reporteCarrera.reporteCarreraResponse.ReporteCarreraResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.function.Supplier;

@Service
public class CarreraService {

    @Autowired
    private final CarreraRepository carreraRepository;

    public CarreraService(final CarreraRepository carreraRepository) {
        this.carreraRepository = carreraRepository;
    }

    @Transactional
    public CarreraResponseDTO cargarCarrera(CarreraRequestDTO request){
        final var carrera = new Carrera(request);
        final var result = this.carreraRepository.save(carrera);
        long inscriptos = 0;

        return new CarreraResponseDTO(result.getCarrera(), result.getDuracion(), inscriptos);
    }

    @Transactional(readOnly = true)
    public List<CarreraResponseDTO> buscarCarrerasConEstudiantes(){
        return this.carreraRepository.buscarCarrerasConEstudiantes()
                .stream()
                .map(CarreraResponseDTO::new)
                .toList();
    }

}
