package tudai.integrador3.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tudai.integrador3.domain.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
importtudai.integrador3.service.dto.estudiante.estudianteResponse.EstudianteResponseDTO;

import java.util.List;
import java.util.Optional;
@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {

    @Query("SELECT new tudai.integrador3.service.dto.estudiante.estudianteResponse.EstudianteResponseDTO(e.DNI, e.nombre, e.apellido,e.edad, e.genero, e.ciudad, e.LU) FROM Estudiante e WHERE e.LU = :LU")
    Optional<EstudianteResponseDTO> buscarEstudianteLU(int LU);

    @Query("SELECT new tudai.integrador3.service.dto.estudiante.estudianteResponse.EstudianteResponseDTO(e.DNI, e.nombre, e.apellido,e.edad, e.genero, e.ciudad, e.LU) FROM Estudiante e WHERE e.genero = :genero")
    List<EstudianteResponseDTO> buscarEstudiantesGenero(String genero);

    @Query("SELECT new tudai.integrador3.service.dto.estudiante.estudianteResponse.EstudianteResponseDTO(e.DNI, e.nombre, e.apellido,e.edad, e.genero, e.ciudad, e.LU) FROM Estudiante e ORDER BY e.apellido")
    List<EstudianteResponseDTO> buscarEstudiantesApellido();

    @Query("SELECT new tudai.integrador3.service.dto.estudiante.estudianteResponse.EstudianteResponseDTO(e.DNI, e.nombre, e.apellido,e.edad, e.genero, e.ciudad, e.LU) FROM Estudiante e JOIN e.carreras ec " +
            "WHERE ec.carrera.carrera = :carrera AND e.ciudad = :ciudad")
    List<EstudianteResponseDTO> buscarEstudiantesCarreraCiudad(String carrera, String ciudad);

}
