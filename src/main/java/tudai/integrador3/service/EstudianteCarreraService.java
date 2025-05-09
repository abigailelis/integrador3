package tudai.integrador3.service;

import tudai.integrador3.domain.EstudianteCarrera;
import tudai.integrador3.domain.EstudianteCarreraKey;
import tudai.integrador3.repository.EstudianteCarreraRepository;
import tudai.integrador3.service.dto.carrera.carreraResponse.CarreraResponseDTO;
import tudai.integrador3.service.dto.estudianteCarrera.estudianteCarreraRequest.reporteCarreraRequest.EstudianteCarreraRequestDTO;
import tudai.integrador3.service.dto.estudianteCarrera.estudianteCarreraResponse.reporteCarreraResponse.EstudianteCarreraResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstudianteCarreraService {

    private final EstudianteCarreraRepository ecRepository;

    @Transactional
    public EstudianteCarreraResponseDTO matricularEstudiante(EstudianteCarreraRequestDTO request){
        final var estudianteCarrera = new EstudianteCarrera(request);
        final var result = this.ecRepository.save(estudianteCarrera);

        int inscripcion = result.getInscripcion();
        int graduacion = result.getGraduacion();
        int antiguedad = result.getAntiguedad();
        int dni_estudiante = result.getId().getId_estudiante();
        String carrera = result.getCarrera().getCarrera();

        return new EstudianteCarreraResponseDTO(inscripcion, graduacion , antiguedad, dni_estudiante, carrera);
    }

    @Transactional(readOnly = true)
    public List<CarreraResponseDTO> buscarCarrerasConEstudiantes(){
        return this.ecRepository.buscarCarrerasConEstudiantes()
                .stream()
                .map(CarreraResponseDTO::new)
                .toList();
    }


}
