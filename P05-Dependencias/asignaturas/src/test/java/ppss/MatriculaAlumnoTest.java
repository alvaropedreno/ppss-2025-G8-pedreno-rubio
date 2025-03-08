package ppss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatriculaAlumnoTest {

    @Test
    void C1_should_return_justificante_with_errors() {
        MatriculaAlumno matriculaAlumno = new MatriculaAlumno() {
            @Override
            protected Operacion getOperacion() {
                return new OperacionStub();
            }
        };

        String dni = "00000000T";
        String[] asignaturas = {"MD", "ZZ", "FBD", "P1"};

        JustificanteMatricula justificante = matriculaAlumno.validaAsignaturas(dni, asignaturas);

        assertEquals("00000000T", justificante.getDni());
        assertTrue(justificante.getAsignaturas().contains("MD"));
        assertTrue(justificante.getAsignaturas().contains("FBD"));
        assertTrue(justificante.getErrores().contains("Asignatura ZZ no existe"));
        assertTrue(justificante.getErrores().contains("Asignatura P1 ya cursada"));
    }

    @Test
    void C2_should_return_justificante_without_errors() {
        MatriculaAlumno matriculaAlumno = new MatriculaAlumno() {
            @Override
            protected Operacion getOperacion() {
                return new OperacionStub();
            }
        };

        String dni = "00000000T";
        String[] asignaturas = {"PPSS", "ADA", "P3"};

        JustificanteMatricula justificante = matriculaAlumno.validaAsignaturas(dni, asignaturas);

        assertEquals("00000000T", justificante.getDni());
        assertTrue(justificante.getAsignaturas().contains("PPSS"));
        assertTrue(justificante.getAsignaturas().contains("ADA"));
        assertTrue(justificante.getAsignaturas().contains("P3"));
        assertTrue(justificante.getErrores().isEmpty());
    }
}
