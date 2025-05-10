package tudai.integrador3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import tudai.integrador3.domain.Estudiante;
import tudai.integrador3.repository.EstudianteRepository;
import tudai.integrador3.service.dto.estudiante.estudianteRequest.EstudianteRequestDTO;
import tudai.integrador3.service.dto.estudiante.estudianteResponse.EstudianteResponseDTO;
import tudai.integrador3.service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Supplier;

@Service

public class EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    /**
     * Guarda un nuevo estudiante en la base de datos.
     * Convierte un {@link EstudianteRequestDTO} en un objeto {@link Estudiante} y lo guarda en el repositorio.
     * @param request un objeto {@link EstudianteRequestDTO} con los datos del estudiante a registrar
     * @return un {@link EstudianteResponseDTO} con la información del estudiante guardado
     */

    @Transactional
    public EstudianteResponseDTO altaEstudiante(EstudianteRequestDTO request){
        final var estudiante = new Estudiante(request);
        int id_estudiante = estudiante.getDNI();

        if(estudianteRepository.findById(id_estudiante).isPresent()){
            final var result = this.estudianteRepository.save(estudiante);
            return new EstudianteResponseDTO(id_estudiante, result.getNombre(), result.getApellido(), result.getEdad(), result.getGenero(), result.getCiudad(), result.getLU());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estudiante con DNI " + id_estudiante + " ya existe.");
        }
    }

    /**
     * Obtiene todos los estudiantes
     * @return una lista de todos los estudiantes de la base de datos.
     */

    @Transactional(readOnly = true)
    public List<EstudianteResponseDTO> findAll(){
        return obtenerEstudiantesDTO(estudianteRepository::findAll);
    }

    /**
     * Busca un estudiante por número de LU.
     * <p>Si el estudiante es encontrado, se transforma en un {@link EstudianteResponseDTO}.
     * En caso contrario, lanza una excepción {@link NotFoundException}.</p>
     * @param LU número de libreta universitaria del estudiante a buscar.
     * @return un {@link EstudianteResponseDTO} con la información del estudiante encontrado
     * @throws NotFoundException si el estudiante con el LU especificado no existe
     */

    @Transactional(readOnly = true)
    public EstudianteResponseDTO buscarEstudianteLU(int LU){
        return this.estudianteRepository.buscarEstudianteLU(LU)
                .map(EstudianteResponseDTO::new)
                .orElseThrow(() -> new NotFoundException("Estudiante", "LU", LU ));
    }

    /**
     * Obtiene la lista de estudiantes con el género especificado
     * @param genero el género de los estudiantes a buscar
     * @return una lista de estudiantes filtrados por género
     */

    @Transactional(readOnly = true)
    public List<EstudianteResponseDTO> buscarEstudiantesGenero(String genero){
        return  obtenerEstudiantesDTO(() -> estudianteRepository.buscarEstudiantesGenero(genero));
    }

    /**
     * Obtiene una lista de los estudiantes ordenados por apellido y los transforma en objetos {@link EstudianteResponseDTO}.
     * @return una lista de estudiantes ordenados por apellido
     */

    @Transactional(readOnly = true)
    public List<EstudianteResponseDTO> buscarEstudiantesApellido(){
        return obtenerEstudiantesDTO(estudianteRepository::buscarEstudiantesApellido);
    }

    /**
     * Filtra los estudiantes que pertenecen a la carrera y ciudad especificadas y los transforma en objetos {@link EstudianteResponseDTO}.
     * @param carrera la carrera universitaria de los estudiantes a buscar
     * @param ciudad la ciudad de residencia de los estudiantes a buscar
     * @return una lista de objetos {@link EstudianteResponseDTO} con los estudiantes filtrados por carrera y ciudad
     */

    @Transactional(readOnly = true)
    public List<EstudianteResponseDTO> buscarEstudiantesCarreraCiudad(String carrera, String ciudad){
        return obtenerEstudiantesDTO(() -> estudianteRepository.buscarEstudiantesCarreraCiudad(carrera, ciudad));
    }

    /**
     * Obtiene una lista de objetos EstudianteResponseDTO a partir de una consulta de Estudiantes.
     * @param consulta un {@link Supplier} que proporciona una lista de objetos {@link Estudiante}
     * @return una lista de objetos {@link EstudianteResponseDTO} transformados a partir de la consulta
     */

    public List<EstudianteResponseDTO> obtenerEstudiantesDTO(Supplier<List<Estudiante>> consulta) {
        return consulta.get()
                .stream()
                .map(EstudianteResponseDTO::new)
                .toList();
    }
}
