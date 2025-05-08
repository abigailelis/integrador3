package tudai.integrador3.controller;

import tudai.integrador3.service.EstudianteCarreraService;
import tudai.integrador3.service.dto.carrera.carreraResponse.CarreraResponseDTO;
import tudai.integrador3.service.dto.estudianteCarrera.estudianteCarreraRequest.reporteCarreraRequest.EstudianteCarreraRequestDTO;
import tudai.integrador3.service.dto.estudianteCarrera.estudianteCarreraResponse.reporteCarreraResponse.EstudianteCarreraResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/carreras/estudiantes")
@RequiredArgsConstructor
public class EstudianteCarreraController {

    private final EstudianteCarreraService ecService;

    //Devuelve una lista de carreras con la cantidad de inscriptos
    @GetMapping("/inscriptos")
    public List<CarreraResponseDTO> buscarCarrerasConEstudiantes(){
        return this.ecService.buscarCarrerasConEstudiantes();
    }

    //Matricula un estudiante en una carrera
    @PostMapping("")
    public ResponseEntity<EstudianteCarreraResponseDTO> matricularEstudiante(@RequestBody @Valid EstudianteCarreraRequestDTO request){
        final var result = this.ecService.matricularEstudiante(request);
        return ResponseEntity.accepted().body(result);
    }
}
