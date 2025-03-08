package ppss;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AlquilaCochesTest {
    IService s;
    AlquilaCochesTestable al;

    @BeforeEach
    public void setup(){
        //Asumimos	 que	 el	 precio	 base	 para	 cualquier	 día	 es	 de	 10	 euros
        s = new ServicioStub(10);
        al = new AlquilaCochesTestable(s);

    }

    @Test
    void C1_should_return_Ticket75_when_tipo_turismo_fecha_2024_5_18_dias_10_festivo_false(){

        LocalDate fecha = LocalDate.of(2023,5,18);
        CalendarioStub c = new CalendarioStub(new ArrayList<>(), new ArrayList<>());
        al.setCalendario(c);
        Ticket resultado = assertDoesNotThrow(()->al.calculaPrecio(TipoCoche.TURISMO,fecha,10));
        assertEquals(75, resultado.getPrecio_final());
    }

    @Test
    void C2_should_return_Ticket62_5_when_tipo_caravana_fecha_2024_6_19_dias_7_festivo_20_24(){

        LocalDate fecha = LocalDate.of(2023,6,19);
        CalendarioStub c = new CalendarioStub(Arrays.asList(20,24), new ArrayList<>());
        al.setCalendario(c);
        Ticket resultado = assertDoesNotThrow(()->al.calculaPrecio(TipoCoche.CARAVANA,fecha,7));
        assertEquals(62.5, resultado.getPrecio_final());
    }

    @Test
    void C3_should_return_MensajeException_when_tipo_turismo_fecha_2024_4_17_dias_8_festivo_false_except_18_21_22(){
        LocalDate fecha = LocalDate.of(2024,4,17);
        CalendarioStub c = new CalendarioStub(new ArrayList<>(), Arrays.asList(18,21,22));
        al.setCalendario(c);
        MensajeException resultado = assertThrows(MensajeException.class,()->al.calculaPrecio(TipoCoche.TURISMO,fecha,8));
        assertEquals("Error en dia: 2024-04-18; Error en dia: 2024-04-21; Error en dia: 2024-04-22; ", resultado.getMessage());
    }
}