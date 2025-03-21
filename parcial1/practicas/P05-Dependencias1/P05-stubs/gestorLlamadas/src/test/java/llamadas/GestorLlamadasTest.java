package llamadas;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class GestorLlamadasTest {

    GestorLlamadasTestable gestor;
    CalendarioStub calendario;

    @BeforeEach
    void setUp(){
        gestor = new GestorLlamadasTestable();
        calendario = new CalendarioStub();
    }

    @Test
    void C1_should_return_207_when_hour_12_min_10(){
        // Arrange
        calendario.setHora(12);
        int minutos = 10;
        gestor.setCalendario(calendario);

        // Act
        double resultado = gestor.calculaConsumo(minutos);

        // Assert
        assertEquals(207, resultado);
    }

    @Test
    void C2_should_return_122_when_hour_21_min_10(){
        // Arrange
        calendario.setHora(21);
        int minutos = 10;
        gestor.setCalendario(calendario);

        // Act
        double resultado = gestor.calculaConsumo(minutos);

        // Assert
        assertEquals(122, resultado);
    }
}