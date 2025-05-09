package tudai.integrador3.domain;

import tudai.integrador3.service.dto.estudiante.estudianteRequest.EstudianteRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * Representa a un estudiante con información personal y la lista de carreras en las que está matriculado.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estudiante {
    @Id
    private int DNI;

    @Column (nullable = false)
    private String nombre;

    @Column (nullable = false)
    private String apellido;

    @Column (nullable = false)
    private int edad;

    @Column (nullable = false)
    private String genero;

    @Column (nullable = false)
    private String ciudad;

    @Column (nullable = false)
    private int LU;

    /**
     * Lista de carreras en las que el estudiante está matriculado.
     */
    @OneToMany(mappedBy = "estudiante")
    private List<EstudianteCarrera> carreras;

    public Estudiante(EstudianteRequestDTO request) {
        this.DNI = request.getDNI();
        this.nombre = request.getNombre();
        this.apellido = request.getApellido();
        this.edad = request.getEdad();
        this.genero = request.getGenero();
        this.ciudad = request.getCiudad();
        this.LU = request.getLU();
    }

}
