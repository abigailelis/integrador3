package tudai.integrador3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import tudai.integrador3.domain.Carrera;
import tudai.integrador3.repository.CarreraRepository;
import tudai.integrador3.service.dto.carrera.carreraRequest.CarreraRequestDTO;
import tudai.integrador3.service.dto.carrera.carreraResponse.CarreraResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CarreraService {

    @Autowired
    private CarreraRepository carreraRepository;

    //Persiste una carrera en la base de datos si no existe el id sino arroja un error.
    @Transactional
    public CarreraResponseDTO cargarCarrera(CarreraRequestDTO request){
        Carrera carrera = new Carrera(request);
        String nombre = carrera.getCarrera();

        if(carreraRepository.findByName(nombre) == null){
            final var result = this.carreraRepository.save(carrera);
            long inscriptos = 0;
            return new CarreraResponseDTO(result.getCarrera(), result.getDuracion(), inscriptos);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La carrera con el nombre " + nombre + " ya existe.");
        }
    }

    //Obtiene una lista de CarreraResponseDTO con la cantidad de inscriptos correspondientes
    @Transactional(readOnly = true)
    public List<CarreraResponseDTO> buscarCarrerasConEstudiantes(){
        return this.carreraRepository.buscarCarrerasConEstudiantes();
    }

}
