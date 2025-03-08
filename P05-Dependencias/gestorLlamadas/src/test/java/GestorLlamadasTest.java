import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.provider.ValueSource;

class GestorLlamadasTest {
    //Usamos el clon
    GestorLlamadasTesteable g;

    @BeforeEach
    void setup(){g = new GestorLlamadasTesteable();}

    @Test
    void C1_should_return_207_when_hour_12_and_min_10(){
        int minutos = 10;
        int hora = 12;
        double resultadoEsperado = 207;

        g.setHoraActual(hora);

        double resultadoObtenido = g.calculaConsumo(minutos);
        assertEquals(resultadoEsperado,resultadoObtenido);
    }

    @Test
    void C2_should_return_207_when_hour_21_and_min_10(){
        int minutos = 10;
        int hora = 21;
        double resultadoEsperado = 122;

        g.setHoraActual(hora);
        double resultadoObtenido = g.calculaConsumo(minutos);
        assertEquals(resultadoEsperado,resultadoObtenido);
    }
}