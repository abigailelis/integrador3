package tudai.integrador3.repository;

import org.springframework.stereotype.Repository;
import tudai.integrador3.domain.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tudai.integrador3.service.dto.estudiante.estudianteResponse.EstudianteResponseDTO;
import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {

    //Remplaza el findAll() de JpaRepository para que todas devuelvan DTO y no mapearlo en el servicio
    @Query("SELECT new tudai.integrador3.service.dto.estudiante.estudianteResponse.EstudianteResponseDTO(e.DNI, e.nombre, e.apellido,e.edad, e.genero, e.ciudad, e.LU) FROM Estudiante e")
    List<EstudianteResponseDTO> obtenerTodosLosEstudiantes();

    //Obtiene un estudianteDTO por libreta universitaria
    @Query("SELECT new tudai.integrador3.service.dto.estudiante.estudianteResponse.EstudianteResponseDTO(e.DNI, e.nombre, e.apellido,e.edad, e.genero, e.ciudad, e.LU) FROM Estudiante e WHERE e.LU = :LU")
    Optional<EstudianteResponseDTO> buscarEstudianteLU(int LU);

    //Obtiene todos los estudiantes filtrados por género(recibido por parámetro)
    @Query("SELECT new tudai.integrador3.service.dto.estudiante.estudianteResponse.EstudianteResponseDTO(e.DNI, e.nombre, e.apellido,e.edad, e.genero, e.ciudad, e.LU) FROM Estudiante e WHERE e.genero = :genero")
    List<EstudianteResponseDTO> buscarEstudiantesGenero(String genero);

    //Obtiene todos los estudiantes ordenados por apellido ascendentemente
    @Query("SELECT new tudai.integrador3.service.dto.estudiante.estudianteResponse.EstudianteResponseDTO(e.DNI, e.nombre, e.apellido,e.edad, e.genero, e.ciudad, e.LU) FROM Estudiante e ORDER BY e.apellido")
    List<EstudianteResponseDTO> buscarEstudiantesApellido();

    //Obtiene todos los estudiantes de una carrera y una ciudad específica (recibidas por parámetro)
    @Query("SELECT new tudai.integrador3.service.dto.estudiante.estudianteResponse.EstudianteResponseDTO(e.DNI, e.nombre, e.apellido,e.edad, e.genero, e.ciudad, e.LU) FROM Estudiante e JOIN e.carreras ec " +
            "WHERE ec.carrera.carrera = :carrera AND e.ciudad = :ciudad")
    List<EstudianteResponseDTO> buscarEstudiantesCarreraCiudad(String carrera, String ciudad);

}
