package ppss;

import org.junit.jupiter.api.*;
import ppss.excepciones.ReservaException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReservaTest {
    ReservaTestable reservaTestable;
    OperacionFactory operacionFactory;
    IOperacionStub operacionStub;

    @BeforeEach
    void setUp() {
        reservaTestable = new ReservaTestable();
        operacionFactory = new OperacionFactory();
        operacionStub = new IOperacionStub();

        reservaTestable.setValidLogin("ppss");
        reservaTestable.setValidPassword("ppss");

        operacionFactory.setOperacionBO(operacionStub);
    }

    @Test
    void C3(){
        String[] ibns = {"11111", "33333", "44444"};
        operacionStub.setIsbnInvalidos(new ArrayList<String>(List.of("33333", "44444")));

        ReservaException exception = assertThrows(ReservaException.class, () -> reservaTestable.realizaReserva("ppss", "ppss", "Luis", ibns));

        assertEquals(exception.getMessage(), "Luis");
    }
}