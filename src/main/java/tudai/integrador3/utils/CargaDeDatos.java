package tudai.integrador3.utils;


import tudai.integrador3.domain.*;
import tudai.integrador3.repository.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

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

    /**
     * Carga los datos desde los csv de estudiantes, carreras y estudianteCarrera a la base de datos
     * @throws IOException
     */
    public void cargarDatosCSV() throws IOException {
        File estudianteCSV = ResourceUtils.getFile("src/main/resources/csv/estudiantes.csv");
        File carreraCSV = ResourceUtils.getFile("src/main/resources/csv/carreras.csv");
        File estudianteCarreraCSV = ResourceUtils.getFile("src/main/resources/csv/estudianteCarrera.csv");

        cargarEstudianteCSV(estudianteCSV);
        cargarCarreraCSV(carreraCSV);
        cargarEstudianteCarreraCSV(estudianteCarreraCSV);

    }

    /**
     * Carga los datos desde el csv de estudiantes
     * @param estudianteCSV path del archivo csv
     * @throws IOException
     */
    public void cargarEstudianteCSV(File estudianteCSV) throws IOException {
        try (FileReader reader = new FileReader(estudianteCSV);
             CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {

            for (CSVRecord csvRecord : csvParser) {
                int id_estudiante = Integer.parseInt(csvRecord.get("DNI"));

                if(!existeEstudiante(id_estudiante))
                    persistEstudiante(id_estudiante, csvRecord);
                else
                    System.out.println("No se pudo agregar al estudiante, ya existe el DNI: " + id_estudiante);
            }
        }
    }

    /**
     * Carga los datos desde el csv de carreras
     * @param carreraCSV path del archivo csv
     * @throws IOException
     */
    public void cargarCarreraCSV(File carreraCSV) throws IOException {
        try (FileReader reader = new FileReader(carreraCSV);
             CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {

            for (CSVRecord csvRecord : csvParser) {
                int id_carrera = Integer.parseInt(csvRecord.get("id_carrera"));

                if(!existeCarrera(id_carrera))
                    persistCarrera(id_carrera, csvRecord);
                else
                    System.out.println("No se pudo agregar la carrera, ya existe el id: " + id_carrera);
            }
        }
    }

    /**
     * Carga los datos desde el csv de estudianteCarrera
     * @param estudianteCarreraCSV path del archivo csv
     * @throws IOException
     */
    public void cargarEstudianteCarreraCSV(File estudianteCarreraCSV) throws IOException {
        try (FileReader reader = new FileReader(estudianteCarreraCSV);
             CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader)) {

            for (CSVRecord csvRecord : csvParser) {
                int id_estudiante = Integer.parseInt(csvRecord.get("id_estudiante"));
                int id_carrera = Integer.parseInt(csvRecord.get("id_carrera"));

                EstudianteCarreraKey estudianteCarreraKey = crearKey(id_estudiante, id_carrera);

                if (verificarMatriculacion(id_estudiante, id_carrera, estudianteCarreraKey))
                    persistEstudianteCarrera(estudianteCarreraKey, csvRecord);
                else
                    System.out.println("No se puede matricular al estudiante");
            }
        }
    }

    /**
     * Crea una clave primaria compuesta por el DNI del estudiante y el id de la carrera a matricular
     * @param id_estudiante DNI del estudiante
     * @param id_carrera id de la carrera
     * @return clave compuesta
     */
    public EstudianteCarreraKey crearKey(int id_estudiante, int id_carrera) {
        EstudianteCarreraKey estudianteCarreraKey = new EstudianteCarreraKey();
        estudianteCarreraKey.setId_estudiante(id_estudiante);
        estudianteCarreraKey.setId_carrera(id_carrera);

        return estudianteCarreraKey;
    }

    /**
     * Persiste a un estudiante en la base de datos
     * @param id_estudiante DNI del estudiante a persistir
     * @param csvRecord fila con los datos del estudiante obtenida en el csv
     */
    public void persistEstudiante(int id_estudiante, CSVRecord csvRecord){
        Estudiante estudiante = new Estudiante();
        estudiante.setDNI(id_estudiante);
        estudiante.setNombre(csvRecord.get("nombre"));
        estudiante.setApellido(csvRecord.get("apellido"));
        estudiante.setEdad(Integer.parseInt(csvRecord.get("edad")));
        estudiante.setGenero(csvRecord.get("genero"));
        estudiante.setCiudad(csvRecord.get("ciudad"));
        estudiante.setLU(Integer.parseInt(csvRecord.get("LU")));

        estudianteRepository.save(estudiante);
    }

    /**
     * Persiste a una carrera en la base de datos
     * @param id_carrera id de la carrera a persistir
     * @param csvRecord fila con los datos de la carrera obtenida en el csv
     */
    public void persistCarrera(int id_carrera, CSVRecord csvRecord){
        Carrera carrera = new Carrera();
        carrera.setId_carrera(id_carrera);
        carrera.setCarrera(csvRecord.get("carrera"));
        carrera.setDuracion(Integer.parseInt(csvRecord.get("duracion")));

        carreraRepository.save(carrera);
    }

    /**
     * Persiste a un estudiante en una carrera determinada en la base de datos
     * @param estudianteCarreraKey id compuesto por el DNI del estudiante y el id de la carrera
     * @param csvRecord fila con los datos del estudiante y la carrera obtenida en el csv
     */
    public void persistEstudianteCarrera(EstudianteCarreraKey estudianteCarreraKey, CSVRecord csvRecord){
        EstudianteCarrera estudianteCarrera = new EstudianteCarrera();
        estudianteCarrera.setId(estudianteCarreraKey);
        estudianteCarrera.setInscripcion(Integer.parseInt(csvRecord.get("inscripcion")));
        estudianteCarrera.setGraduacion(Integer.parseInt(csvRecord.get("graduacion")));
        estudianteCarrera.setAntiguedad(Integer.parseInt(csvRecord.get("antiguedad")));

        ecRepository.save(estudianteCarrera);
    }

    /**
     * Verifica si existe un estudiante en la base de datos
     * @param id_estudiante DNI del estudiante a verificar
     * @return boolean
     */
    public boolean existeEstudiante(int id_estudiante){
        return estudianteRepository.findById(id_estudiante).isPresent();
    }

    /**
     * Verifica si existe una carrera en la base de datos
     * @param id_carrera id de la carrera a verificar
     * @return boolean
     */
    public boolean existeCarrera(int id_carrera){
        return carreraRepository.findById(id_carrera).isPresent();
    }

    /**
     * Verifica si el estudiante está o no matriculado en la carrera
     * @param id_estudiante DNI del estudiante a verificar
     * @param id_carrera id de la carrera a verificar
     * @param id_estudianteCarreraKey clave compuesta por el DNI del estudiante y el id de la carrera
     * @return boolean
     */
    public boolean verificarMatriculacion(int id_estudiante, int id_carrera, EstudianteCarreraKey id_estudianteCarreraKey) {
        EstudianteCarrera matriculado = null;

        if(existeEstudiante(id_estudiante) && existeCarrera(id_carrera))
            matriculado = ecRepository.findById(id_estudianteCarreraKey);

        return matriculado == null;
    }

}
