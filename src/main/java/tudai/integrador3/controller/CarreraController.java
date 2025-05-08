package tudai.integrador3.controller;

import tudai.integrador3.service.CarreraService;
import tudai.integrador3.service.dto.carrera.carreraRequest.CarreraRequestDTO;
import tudai.integrador3.service.dto.carrera.carreraResponse.CarreraResponseDTO;
import tudai.integrador3.service.dto.reporteCarrera.reporteCarreraResponse.ReporteCarreraResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/carreras")
@RequiredArgsConstructor
public class CarreraController {

    private final CarreraService carreraService;

    //Genera reporte de carreras ordenado por año y nombre de carrera
    @GetMapping("/reporte")
    public List<ReporteCarreraResponseDTO>  generarReporteCarreras(){
        return this.carreraService.generarReporteCarreras();
    }

    //Carga una nueva carrera
    @PostMapping("")
    public ResponseEntity<CarreraResponseDTO> cargarCarrera(@RequestBody @Valid CarreraRequestDTO request){
        final var result = this.carreraService.cargarCarrera(request);
        return ResponseEntity.accepted().body(result);
    }
}
