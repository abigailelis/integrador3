package tudai.integrador3.repository;

import tudai.integrador3.domain.Carrera;
import tudai.integrador3.service.dto.reporteCarrera.reporteCarreraResponse.ReporteCarreraResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarreraRepository extends JpaRepository<Carrera, Integer> {

    @Query("SELECT new tudai.integrador3.service.dto.reporteCarrera.reporteCarreraResponse.ReporteCarreraResponseDTO(c.carrera, " +
            "COALESCE(ec.inscripcion, ec.graduacion), " +
            "SUM(CASE WHEN ec.inscripcion IS NOT NULL THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN ec.graduacion IS NOT NULL AND ec.graduacion > 0 THEN 1 ELSE 0 END)) " +
            "FROM EstudianteCarrera ec " +
            "JOIN ec.carrera c " +
            "GROUP BY c.carrera, COALESCE(ec.inscripcion, ec.graduacion) " +
            "ORDER BY c.carrera, COALESCE(ec.inscripcion, ec.graduacion)")
    List<ReporteCarreraResponseDTO> generarReporteCarreras();
}
