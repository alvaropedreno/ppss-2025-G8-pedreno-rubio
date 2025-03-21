package ppss;

import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlquilaCochesTest {
    AlquilaCochesTestable alquilaCochesTestable;
    CalendarioStub calendarioStub;
    ServicioStub servicioStub;


    @BeforeEach
    void setUp(){
        alquilaCochesTestable = new AlquilaCochesTestable();
        calendarioStub = new CalendarioStub();
        servicioStub = new ServicioStub();

        alquilaCochesTestable.setCalendario(calendarioStub);
        alquilaCochesTestable.setServicio(servicioStub);
        servicioStub.setPrecio(10);
    }

    @Test
    void C1(){
        // Act
        TipoCoche tipo = TipoCoche.TURISMO;
        LocalDate fecha = LocalDate.of(2024, 5, 18);
        int dias = 10;
        Ticket ticketEsperado = new Ticket();
        ticketEsperado.setPrecio_final(75);

        // Arrange
        Ticket resultado = assertDoesNotThrow(() -> alquilaCochesTestable.calculaPrecio(tipo, fecha, dias));

        // Assert
        assertEquals(75, resultado.getPrecio_final());
    }

    @Test
    void C2(){
        // Act
        TipoCoche tipo = TipoCoche.CARAVANA;
        LocalDate fecha = LocalDate.of(2024, 6, 19);
        int dias = 7;
        Ticket ticketEsperado = new Ticket();
        ticketEsperado.setPrecio_final(62.5f);
        calendarioStub.setFestivos(new ArrayList<Integer>(List.of(20, 24)));

        // Act
        Ticket resultado = assertDoesNotThrow(() -> alquilaCochesTestable.calculaPrecio(tipo, fecha, dias));

        // Assert
        assertEquals(ticketEsperado.getPrecio_final(), resultado.getPrecio_final());
    }

    @Test
    void C3(){
        // Arrange
        TipoCoche tipo = TipoCoche.CARAVANA;
        LocalDate fecha = LocalDate.of(2024, 4, 17);
        int dias = 8;
        String errorEsperado = "Error en dia: 2024-04-18; Error en dia: 2024-04-21; Error en dia: 2024-04-22; ";
        calendarioStub.setExcepciones(new ArrayList<Integer>(List.of(18, 21, 22)));

        // Act
        MensajeException resultado = assertThrows(MensajeException.class, () -> alquilaCochesTestable.calculaPrecio(tipo, fecha, dias));

        // Assert
        assertEquals(errorEsperado, resultado.getMessage());
    }
}