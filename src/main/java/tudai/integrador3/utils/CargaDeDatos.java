package tudai.integrador3.utils;

import jakarta.persistence.EntityManager;
import tudai.integrador3.domain.Carrera;
import tudai.integrador3.domain.Estudiante;
import tudai.integrador3.domain.EstudianteCarrera;
import tudai.integrador3.domain.EstudianteCarreraKey;
import tudai.integrador3.repository.CarreraRepository;
import tudai.integrador3.repository.EstudianteCarreraRepository;
import tudai.integrador3.repository.EstudianteRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

@Component
public class CargaDeDatos {

    private final EstudianteRepository estudianteRepository;
    private final CarreraRepository carreraRepository;
    private final EstudianteCarreraRepository ecRepository;

    @Autowired
    public CargaDeDatos(EstudianteRepository estudianteRepository, CarreraRepository carreraRepository, EstudianteCarreraRepository ecRepository) {
        this.estudianteRepository = estudianteRepository;
        this.carreraRepository = carreraRepository;
        this.ecRepository = ecRepository;
    }

    public void cargarDatosCSV() throws IOException {
        File estudianteCSV = ResourceUtils.getFile("src/main/resources/csv/estudiantes.csv");
        File carreraCSV = ResourceUtils.getFile("src/main/resources/csv/carreras.csv");
        File estudianteCarreraCSV = ResourceUtils.getFile("src/main/resources/csv/estudianteCarrera.csv");

        cargarEstudianteCSV(estudianteCSV);
        cargarCarreraCSV(carreraCSV);
        cargarEstudianteCarreraCSV(estudianteCarreraCSV);

    }

    public void cargarEstudianteCSV(File estudianteCSV) throws IOException {
        try (FileReader reader = new FileReader(estudianteCSV);
             CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {

            //DNI,nombre,apellido,edad,genero,ciudad,LU
            for (CSVRecord csvRecord : csvParser) {
                Estudiante estudiante = new Estudiante();
                estudiante.setDNI(Integer.parseInt(csvRecord.get("DNI")));
                estudiante.setNombre(csvRecord.get("nombre"));
                estudiante.setApellido(csvRecord.get("apellido"));
                estudiante.setEdad(Integer.parseInt(csvRecord.get("edad")));
                estudiante.setGenero(csvRecord.get("genero"));
                estudiante.setCiudad(csvRecord.get("ciudad"));
                estudiante.setLU(Integer.parseInt(csvRecord.get("LU")));

                estudianteRepository.save(estudiante);
            }
        }
    }

    public void cargarCarreraCSV(File carreraCSV) throws IOException {
        try (FileReader reader = new FileReader(carreraCSV);
             CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {

            //id_carrera,carrera,duracion
            for (CSVRecord csvRecord : csvParser) {
                Carrera carrera = new Carrera();
                carrera.setId_carrera(Integer.parseInt(csvRecord.get("id_carrera")));
                carrera.setCarrera(csvRecord.get("carrera"));
                carrera.setDuracion(Integer.parseInt(csvRecord.get("duracion")));

                carreraRepository.save(carrera);
            }
        }
    }

    public void cargarEstudianteCarreraCSV(File estudianteCarreraCSV) throws IOException {
        try (FileReader reader = new FileReader(estudianteCarreraCSV);
             CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {

            //id_estudiante,id_carrera,inscripcion,graduacion,antiguedad
            for (CSVRecord csvRecord : csvParser) {

                EstudianteCarreraKey estudianteCarreraKey = crearKey(Integer.parseInt(csvRecord.get("id_estudiante")), Integer.parseInt(csvRecord.get("id_carrera")));

                if (verificarMatriculacion(estudianteCarreraKey.getId_estudiante(), estudianteCarreraKey.getId_carrera(), estudianteCarreraKey)) {
                    EstudianteCarrera estudianteCarrera = new EstudianteCarrera();
                    estudianteCarrera.setId(estudianteCarreraKey);
                    estudianteCarrera.setInscripcion(Integer.parseInt(csvRecord.get("inscripcion")));
                    estudianteCarrera.setGraduacion(Integer.parseInt(csvRecord.get("graduacion")));
                    estudianteCarrera.setAntiguedad(Integer.parseInt(csvRecord.get("antiguedad")));

                    ecRepository.save(estudianteCarrera);

                } else {
                    System.out.println("No se puede matricular al estudiante");
                }

            }
        }
    }

    public EstudianteCarreraKey crearKey(int id_estudiante, int id_carrera) {
        EstudianteCarreraKey estudianteCarreraKey = new EstudianteCarreraKey();
        estudianteCarreraKey.setId_estudiante(id_estudiante);
        estudianteCarreraKey.setId_carrera(id_carrera);

        return estudianteCarreraKey;
    }

    public boolean verificarMatriculacion(int id_estudiante, int id_carrera, EstudianteCarreraKey id_estudianteCarreraKey) {
        EstudianteCarrera matriculado = ecRepository.findById(id_estudianteCarreraKey);
        Optional<Estudiante> estudiante = estudianteRepository.findById(id_estudiante);
        Optional<Carrera> carrera = carreraRepository.findById(id_carrera);

        return matriculado == null && estudiante.isPresent() && carrera.isPresent();
    }

}
