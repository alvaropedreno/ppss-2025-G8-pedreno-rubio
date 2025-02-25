package ppss;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests asociados a la clase Cine")
class CineTest {

    @Test
    @DisplayName("C1: Debe lanzar ButacasException cuando la fila está vacía y se solicitan 3 asientos")
    void C1_reservaButacas_should_return_Exception_when_fila_empty_and_want_3(){
        Cine cine = new Cine();
        boolean[] asientos = {};

        ButacasException excepcion = assertThrows(ButacasException.class, () -> cine.reservaButacas(asientos, 3));

        assertEquals("No se puede procesar la solicitud", excepcion.getMessage());    }

    @Test
    @DisplayName("C2: Debe devolver Falso cuando la fila está vacia y se solicitan 0 asientos")
    void C2_reservaButacas_should_return_false_when_fila_empty_and_want_zero(){
        Cine cine = new Cine();
        boolean[] asientos = {};

        boolean resultado = assertDoesNotThrow(() -> cine.reservaButacas(asientos, 0));

        assertAll("C2 Debe devolver falso y no cambiar la lista de asientos",
                () -> assertFalse(resultado, "El método debería devolver false"),
                () -> assertArrayEquals(new boolean[]{}, asientos, "El array de asientos no debería cambiar")
        );
    }

    @Test
    @DisplayName("C3: Debe devolver True cuando la fila tiene 3 sitios y se solicitan 2 asientos")
    void C3_reservaButacas_should_return_true_when_fila_has_3_seats_free_and_want_2(){
        Cine cine = new Cine();
        boolean[] asientos = {false, false, false, true, true};

        boolean resultado = assertDoesNotThrow(() -> cine.reservaButacas(asientos, 2));

        assertAll("C3 Debe devolver verdadero y cambiar la lista de asientos",
                () -> assertTrue(resultado, "El método debería devolver false"),
                () -> assertArrayEquals(new boolean[]{true, true, false, true, true}, asientos, "El array de asientos debería cambiar: ")
        );
    }

    @Test
    @DisplayName("C4: Debe devolver falso cuando no hay asientos libres y se solicita uno")
    void C4_reservaButacas_should_return_false_when_no_free_seats_and_want_1(){
        Cine cine = new Cine();
        boolean[] asientos = {true, true, true};

        boolean resultado = assertDoesNotThrow(() -> cine.reservaButacas(asientos, 1));

        assertAll("C4 debe devolver falso y no cambiar la listax",
                () -> assertFalse(resultado, "El método debería devolver false"),
                () -> assertArrayEquals(new boolean[]{true, true, true}, asientos, "El array de asientos no debería cambiar: ")
        );
    }

}
