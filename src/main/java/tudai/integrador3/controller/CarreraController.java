package tudai.integrador3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import tudai.integrador3.service.CarreraService;
import tudai.integrador3.service.dto.carrera.carreraRequest.CarreraRequestDTO;
import tudai.integrador3.service.dto.carrera.carreraResponse.CarreraResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/carreras")
public class CarreraController {

    @Autowired
    private CarreraService carreraService;

    //Devuelve una lista de carreras con la cantidad de inscriptos
    @GetMapping("")
    public List<CarreraResponseDTO> buscarCarrerasConEstudiantes(){
        return this.carreraService.buscarCarrerasConEstudiantes();
    }

    //Carga una nueva carrera
    @PostMapping("")
    public ResponseEntity<CarreraResponseDTO> cargarCarrera(@RequestBody @Valid CarreraRequestDTO request){
        final var result = this.carreraService.cargarCarrera(request);
        return ResponseEntity.accepted().body(result);
    }
}
