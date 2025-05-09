package tudai.integrador3.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tudai.integrador3.domain.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {

    @Query("SELECT e FROM Estudiante e WHERE e.LU = :LU")
    Optional<Estudiante> buscarEstudianteLU(int LU);

    @Query("SELECT e FROM Estudiante e WHERE e.genero = :genero")
    List<Estudiante> buscarEstudiantesGenero(String genero);

    @Query("SELECT e FROM Estudiante e ORDER BY e.apellido")
    List<Estudiante> buscarEstudiantesApellido();

    @Query("SELECT e FROM Estudiante e JOIN e.carreras ec " +
            "WHERE ec.carrera.carrera = :carrera AND e.ciudad = :ciudad")
    List<Estudiante> buscarEstudiantesCarreraCiudad(String carrera, String ciudad);

}
