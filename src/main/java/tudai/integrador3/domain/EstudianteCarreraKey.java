package tudai.integrador3.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Objects;

/**
 * Clase que representa una clave compuesta para la relación entre estudiante y carrera.
 */
@Embeddable
@Getter
@Setter
@AllArgsConstructor
public class EstudianteCarreraKey implements Serializable {

    private int id_estudiante;
    private int id_carrera;

    // Constructor vacío (necesario para JPA)
    public EstudianteCarreraKey() {}

    // Implementar equals()
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        EstudianteCarreraKey that = (EstudianteCarreraKey) obj;
        return Objects.equals(id_estudiante, that.id_estudiante) &&
                Objects.equals(id_carrera, that.id_carrera);
    }

    // Implementar hashCode()
    @Override
    public int hashCode() {
        return Objects.hash(id_estudiante, id_carrera);
    }

}
