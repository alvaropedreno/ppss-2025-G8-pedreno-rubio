package ppss;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CineTest {
    @Test
    void C1_should_return_exception_when_asientos_is_empty_and_solicitados_is_3(){
        // Arrange
        boolean[] asientos = {};
        int solicitados = 3;
        Cine cine = new Cine();
        String mensajeEsperado = "No se puede procesar la solicitud";

        // Act
        ButacasException excepcion = assertThrows(ButacasException.class, () -> cine.reservaButacas(asientos, solicitados));

        // Assert
        assertEquals(mensajeEsperado, excepcion.getMessage());
    }

    @Test
    void C2_should_return_false_when_asientos_is_empty_and_solicitados_is_0(){
        // Arrange
        boolean[] asientos = {};
        int solicitados = 0;
        Cine cine = new Cine();

        // Act
        boolean resultado = assertDoesNotThrow(() -> cine.reservaButacas(asientos, solicitados));

        // Assert
        assertAll("C2 debe devolver falso y lista asientos vacia",
                () -> assertFalse(resultado),
                () -> assertArrayEquals(new boolean[]{}, asientos)
        );
    }

    @Test
    void C3_reservaButacas_should_return_true_when_fila_has_3_seats_free_and_want_2(){
        // Arrange
        boolean[] asientos = {false, false, false, true, true};
        int solicitados = 2;
        Cine cine = new Cine();
        boolean[] asientosEsperados = {true, true, false, true, true};

        // Act
        boolean resultado = assertDoesNotThrow(() -> cine.reservaButacas(asientos, solicitados));

        // Assert
        assertAll(
                () -> assertTrue(resultado),
                () -> assertArrayEquals(asientosEsperados, asientos)
        );

    }

    @Test
    void C4_reservaButacas_should_return_false_when_fila_has_0_seats_free_and_want_1(){
        // Arrange
        boolean[] asientos = {true, true, true};
        int solicitados = 1;
        Cine cine = new Cine();
        boolean[] asientosEsperados = {true, true, true};

        // Act
        boolean resultado = assertDoesNotThrow(() -> cine.reservaButacas(asientos, solicitados));

        // Assert
        assertAll(
                () -> assertFalse(resultado),
                () -> assertArrayEquals(asientos, asientosEsperados)
        );
    }

    @ParameterizedTest(name = "reservaButacas_[{index}] should be {1} when we want {0} and {2}")
    @MethodSource("casosDePrueba")
    void C5_parametrized(boolean resultadoEsperado, int solicitados, String mensaje, boolean[] asientos){
        Cine cine = new Cine();

        boolean resultado = assertDoesNotThrow(() -> cine.reservaButacas(asientos, solicitados));

        assertEquals(resultado, resultadoEsperado);
    }

    private static Stream<Arguments> casosDePrueba(){
        return Stream.of(
                Arguments.of(false, 0, "fila has no seats", new boolean[]{}),
                Arguments.of(true, 1, "there are 2 free seats", new boolean[]{false, false}),
                Arguments.of(false, 1, "all seats are already reserved", new boolean[]{true, true})
        );
    }
}