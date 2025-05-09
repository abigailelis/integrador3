package tudai.integrador3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import tudai.integrador3.service.EstudianteService;
import tudai.integrador3.service.dto.estudiante.estudianteRequest.EstudianteRequestDTO;
import tudai.integrador3.service.dto.estudiante.estudianteResponse.EstudianteResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/estudiantes")

public class EstudianteController {

    @Autowired
    private final EstudianteService estudianteService;

    public EstudianteController(final EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    //Obtiene todos los estudiantes
    @GetMapping
    public List<EstudianteResponseDTO> findAll(){
        return estudianteService.findAll();
    }

    //Obtiene estudiante por LU
    @GetMapping("/lu/{LU}")
    public EstudianteResponseDTO buscarEstudianteLU( @PathVariable int LU){
        return this.estudianteService.buscarEstudianteLU(LU);
    }

    //Obtiene estudiantes por género
    @GetMapping("/genero/{genero}")
    public List<EstudianteResponseDTO> buscarEstudiantesGenero(@PathVariable String genero){
        return this.estudianteService.buscarEstudiantesGenero(genero);
    }

    // Obtiene estudiantes por género con query params
    @GetMapping("/genero")
    public List<EstudianteResponseDTO> buscarEstudiantesGeneroQP(@RequestParam String genero) {
        return this.estudianteService.buscarEstudiantesGenero(genero);
    }

    //Obtiene estudiantes ordenados por apellido
    @GetMapping("/orderBy/apellido")
    public List<EstudianteResponseDTO> buscarEstudiantesApellido(){
        return this.estudianteService.buscarEstudiantesApellido();
    }

    //Obtiene estudiantes filtrados por carrera y ciudad
    @GetMapping("/carrera-ciudad/{carrera}/{ciudad}")
    public List<EstudianteResponseDTO> buscarEstudiantesCarreraCiudad( @PathVariable String carrera, @PathVariable String ciudad){
        return this.estudianteService.buscarEstudiantesCarreraCiudad(carrera, ciudad);
    }

    //Da de alta un estudiante
    @PostMapping("")
    public ResponseEntity<EstudianteResponseDTO> altaEstudiante (@RequestBody @Valid EstudianteRequestDTO request){
        final var result = this.estudianteService.altaEstudiante(request);
        return ResponseEntity.accepted().body(result);
    }

}
