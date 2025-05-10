package tudai.integrador3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import tudai.integrador3.domain.Estudiante;
import tudai.integrador3.repository.EstudianteRepository;
import tudai.integrador3.service.dto.estudiante.estudianteRequest.EstudianteRequestDTO;
import tudai.integrador3.service.dto.estudiante.estudianteResponse.EstudianteResponseDTO;
import tudai.integrador3.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    //Da de alta un estudiante nuevo
    @Transactional
    public EstudianteResponseDTO altaEstudiante(EstudianteRequestDTO request){
        final var estudiante = new Estudiante(request);
        int id_estudiante = estudiante.getDNI();

        if(!estudianteRepository.findById(id_estudiante).isPresent()){
            final var result = this.estudianteRepository.save(estudiante);
            return new EstudianteResponseDTO(id_estudiante, result.getNombre(), result.getApellido(), result.getEdad(), result.getGenero(), result.getCiudad(), result.getLU());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estudiante con DNI " + id_estudiante + " ya existe.");
        }
    }

    //Obtiene una lista de todos los estudiantes
    @Transactional(readOnly = true)
    public List<EstudianteResponseDTO> findAll(){
        return this.estudianteRepository.obtenerTodosLosEstudiantes();
    }

   //Obtiene un estudiante por LU si existe y sino arroja un error
    @Transactional(readOnly = true)
    public EstudianteResponseDTO buscarEstudianteLU(int LU){
        return this.estudianteRepository.buscarEstudianteLU(LU)
                .orElseThrow(() -> new NotFoundException("Estudiante", "LU", LU ));
    }

    //Obtiene una lista de todos los estudiantes filtrados por género (recibido por parámetro)
    @Transactional(readOnly = true)
    public List<EstudianteResponseDTO> buscarEstudiantesGenero(String genero){
        return estudianteRepository.buscarEstudiantesGenero(genero);
    }

    //Obtiene la lista de todos los estudiantes ordenados por apellido ascendentemente
    @Transactional(readOnly = true)
    public List<EstudianteResponseDTO> buscarEstudiantesApellido(){
        return estudianteRepository.buscarEstudiantesApellido();
    }

    //Obtiene una lista de todos los estudiantes de una ciudad y una carrera (recibidas por parámetro)
    @Transactional(readOnly = true)
    public List<EstudianteResponseDTO> buscarEstudiantesCarreraCiudad(String carrera, String ciudad){
        return estudianteRepository.buscarEstudiantesCarreraCiudad(carrera, ciudad);
    }
   
}
