package tudai.integrador3.repository;

import org.springframework.stereotype.Repository;
import tudai.integrador3.domain.Carrera;
import tudai.integrador3.service.dto.carrera.carreraResponse.CarreraResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera, Integer> {

    //Obtiene una lista de todas las carreras con la cantidad de inscriptos
    @Query("SELECT new tudai.integrador3.service.dto.carrera.carreraResponse.CarreraResponseDTO(" +
            "c.carrera, c.duracion, " +
            "COUNT(CASE WHEN ec.inscripcion IS NOT NULL AND ec.inscripcion > 0 THEN 1 ELSE 0 END)" +
            ")" +
            "FROM EstudianteCarrera ec " +
            "JOIN ec.carrera c " +
            "GROUP BY c.carrera, c.duracion " +
            "ORDER BY SUM(CASE WHEN ec.inscripcion IS NOT NULL AND ec.inscripcion > 0 THEN 1 ELSE 0 END) DESC")
    List<CarreraResponseDTO> buscarCarrerasConEstudiantes();


    @Query ("SELECT c FROM Carrera c WHERE c.carrera=:carrera")
    Carrera findByName(String carrera);
}
