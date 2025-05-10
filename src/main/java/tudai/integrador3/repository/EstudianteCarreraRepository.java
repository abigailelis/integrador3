package tudai.integrador3.repository;

import org.springframework.stereotype.Repository;
import tudai.integrador3.domain.EstudianteCarrera;
import tudai.integrador3.domain.EstudianteCarreraKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tudai.integrador3.service.dto.reporteCarrera.reporteCarreraResponse.ReporteCarreraResponseDTO;
import java.util.List;

@Repository
public interface EstudianteCarreraRepository extends JpaRepository<EstudianteCarrera, Integer> {

    //Obtiene un reporte de todas las carreras ordenado alfabeticamente por carreras y cronoglogicamente por años
    @Query("SELECT new tudai.integrador3.service.dto.reporteCarrera.reporteCarreraResponse.ReporteCarreraResponseDTO(c.carrera, " +
            "COALESCE(ec.inscripcion, ec.graduacion), " +
            "SUM(CASE WHEN ec.inscripcion IS NOT NULL THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN ec.graduacion IS NOT NULL AND ec.graduacion > 0 THEN 1 ELSE 0 END)) " +
            "FROM EstudianteCarrera ec " +
            "JOIN ec.carrera c " +
            "GROUP BY c.carrera, COALESCE(ec.inscripcion, ec.graduacion) " +
            "ORDER BY c.carrera, COALESCE(ec.inscripcion, ec.graduacion)")
    List<ReporteCarreraResponseDTO> generarReporteCarreras();

    //Obtiene un estudiante en una determinada carrera según clave primaria compuesta por DNI estudiante y id_carrera
    EstudianteCarrera findById(EstudianteCarreraKey idEstudianteCarreraKey);
}
