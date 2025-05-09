package tudai.integrador3.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tudai.integrador3.domain.Carrera;
import tudai.integrador3.service.dto.carrera.carreraResponse.CarreraResponseDTO;
import tudai.integrador3.service.dto.reporteCarrera.reporteCarreraResponse.ReporteCarreraResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
@Repository
public interface CarreraRepository extends JpaRepository<Carrera, Integer> {

    @Query("SELECT new tudai.integrador3.service.dto.carrera.carreraResponse.CarreraResponseDTO(" +
            "c.carrera, c.duracion, " +
            "COUNT(CASE WHEN ec.inscripcion IS NOT NULL AND ec.inscripcion > 0 THEN 1 ELSE 0 END)" +
            ")" +
            "FROM EstudianteCarrera ec " +
            "JOIN ec.carrera c " +
            "GROUP BY c.carrera, c.duracion " +
            "ORDER BY SUM(CASE WHEN ec.inscripcion IS NOT NULL AND ec.inscripcion > 0 THEN 1 ELSE 0 END) DESC")
    List<CarreraResponseDTO> buscarCarrerasConEstudiantes();

}
