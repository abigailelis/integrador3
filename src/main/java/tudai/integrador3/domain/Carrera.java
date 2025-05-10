package tudai.integrador3.domain;

import tudai.integrador3.service.dto.carrera.carreraRequest.CarreraRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.Optional;

/**
 * Representa una carrera universitaria con información sobre el nombre, duración y los estudiantes inscritos.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Carrera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_carrera;

    @Column (nullable = false, unique = true)
    private String carrera;

    @Column (nullable = false)
    private int duracion;

    /**
     * Lista de estudiantes inscritos en la carrera.
     */
    @OneToMany (mappedBy = "carrera")
    private List<EstudianteCarrera> estudiantes;

    public Carrera(CarreraRequestDTO request) {
        this.carrera = request.getCarrera();
        this.duracion = request.getDuracion();
    }

}
