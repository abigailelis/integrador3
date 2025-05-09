package tudai.integrador3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import tudai.integrador3.service.EstudianteCarreraService;
import tudai.integrador3.service.dto.carrera.carreraResponse.CarreraResponseDTO;
import tudai.integrador3.service.dto.estudianteCarrera.estudianteCarreraRequest.reporteCarreraRequest.EstudianteCarreraRequestDTO;
import tudai.integrador3.service.dto.estudianteCarrera.estudianteCarreraResponse.reporteCarreraResponse.EstudianteCarreraResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tudai.integrador3.service.dto.reporteCarrera.reporteCarreraResponse.ReporteCarreraResponseDTO;

import java.util.List;

@RestController
@RequestMapping("api/carreras/estudiantes")
public class EstudianteCarreraController {

    @Autowired
    private final EstudianteCarreraService ecService;

    public EstudianteCarreraController(final EstudianteCarreraService ecService) {
        this.ecService = ecService;
    }

    //Genera reporte de carreras ordenado por año y nombre de carrera
    @GetMapping("/reporte")
    public List<ReporteCarreraResponseDTO>  generarReporteCarreras(){
        return this.ecService.generarReporteCarreras();
    }

    //Matricula un estudiante en una carrera
    @PostMapping("")
    public ResponseEntity<EstudianteCarreraResponseDTO> matricularEstudiante(@RequestBody @Valid EstudianteCarreraRequestDTO request){
        final var result = this.ecService.matricularEstudiante(request);
        return ResponseEntity.accepted().body(result);
    }
}
