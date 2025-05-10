package tudai.integrador3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import tudai.integrador3.domain.Carrera;
import tudai.integrador3.domain.Estudiante;
import tudai.integrador3.domain.EstudianteCarrera;
import tudai.integrador3.domain.EstudianteCarreraKey;
import tudai.integrador3.repository.CarreraRepository;
import tudai.integrador3.repository.EstudianteCarreraRepository;
import tudai.integrador3.repository.EstudianteRepository;
import tudai.integrador3.service.dto.estudianteCarrera.estudianteCarreraRequest.reporteCarreraRequest.EstudianteCarreraRequestDTO;
import tudai.integrador3.service.dto.estudianteCarrera.estudianteCarreraResponse.reporteCarreraResponse.EstudianteCarreraResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tudai.integrador3.service.dto.reporteCarrera.reporteCarreraResponse.ReporteCarreraResponseDTO;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class EstudianteCarreraService {

    @Autowired
    private EstudianteCarreraRepository ecRepository;
    @Autowired
    private CarreraRepository carreraRepository;
    @Autowired
    private EstudianteRepository estudianteRepository;

    //Matricula a un estudiante en una carrera determinada
    @Transactional
    public EstudianteCarreraResponseDTO matricularEstudiante(EstudianteCarreraRequestDTO request){
        int dniEstudiante = request.getDni_estudiante();
        int idCarrera = request.getId_carrera();
        Optional<Carrera> carreraBD = carreraRepository.findById(idCarrera);
        EstudianteCarreraKey key = new EstudianteCarreraKey(dniEstudiante,idCarrera);

        if(verificarMatriculacion(dniEstudiante, idCarrera,key)){
            EstudianteCarrera estudianteCarrera = new EstudianteCarrera();
            int duracionCarrera = carreraBD.get().getDuracion();
            int anioActual = Calendar.getInstance().get(Calendar.YEAR);
            int anioGraduacion = anioActual+ duracionCarrera;
            int antiguedad = 0;

            estudianteCarrera.setId(key);
            estudianteCarrera.setInscripcion(anioActual);
            estudianteCarrera.setGraduacion(anioGraduacion);
            estudianteCarrera.setAntiguedad(antiguedad);
            //realiza la persistencia del dato a guardar
            this.ecRepository.save(estudianteCarrera);

            return new EstudianteCarreraResponseDTO(anioActual, anioGraduacion , antiguedad, dniEstudiante, idCarrera);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estudiante con DNI " + dniEstudiante + " no pudo matricularse.");
        }
    }

    //Genera un reporte de todas las carreras ordenadas alfabéticamente por nombre y cronológicamente por años
    @Transactional(readOnly = true)
    public List<ReporteCarreraResponseDTO> generarReporteCarreras(){
        return this.ecRepository.generarReporteCarreras();
    }

    //Verifica si existe un estudiante en la base de datos
    public boolean existeEstudiante(int id_estudiante){
        return estudianteRepository.existsById(id_estudiante);
    }

    //Verifica si existe una carrera en la base de datos
    public boolean existeCarrera(int id_carrera){
        return carreraRepository.existsById(id_carrera);
    }

    //Verifica si el estudiante está o no matriculado en la carrera
    public boolean verificarMatriculacion(int id_estudiante, int id_carrera, EstudianteCarreraKey id_estudianteCarreraKey) {
        if (existeEstudiante(id_estudiante) && existeCarrera(id_carrera))
            return ecRepository.findById(id_estudianteCarreraKey) == null;
        return false; // Si el estudiante o la carrera no existen, no puede estar matriculado.
    }

}
