package ppss;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MatriculaAlumnoTest {
    MatriculaAlumnoTestable matriculaAlumno;
    OperacionStub operacionStub;

    @BeforeEach
    void setUp(){
        operacionStub = new OperacionStub();
        matriculaAlumno = new MatriculaAlumnoTestable();

        ArrayList<String> cursadas = new ArrayList<>(Arrays.asList("P1", "FC", "FFI"));
        ArrayList<String> invalidas = new ArrayList<>(Arrays.asList("YYY", "ZZ"));

        operacionStub.setCursadas(cursadas);
        operacionStub.setValidas(invalidas);

        matriculaAlumno.setOperacionStub(operacionStub);

    }

    @Test
    void C1(){
        // Arrange
        String dni = "00000000T";
        String[] asignaturas = {"MD", "ZZ", "FBD", "P1"};
        JustificanteMatricula resultadoEsperado = new JustificanteMatricula();
        resultadoEsperado.setDni(dni);
        resultadoEsperado.setAsignaturas(new ArrayList<>(Arrays.asList("MD", "FBD")));
        resultadoEsperado.setErrores(new ArrayList<>(Arrays.asList("Asignatura ZZ no existe", "Asignatura P1 ya cursada")));

        // Act
        JustificanteMatricula resultado = matriculaAlumno.validaAsignaturas(dni, asignaturas);

        // Assert
        assertAll(
                () -> assertEquals(resultado.getDni(), resultadoEsperado.getDni()),
                () -> assertEquals(resultado.getAsignaturas(), resultadoEsperado.getAsignaturas()),
                () -> assertEquals(resultado.getErrores(), resultadoEsperado.getErrores())
        );
    }
}