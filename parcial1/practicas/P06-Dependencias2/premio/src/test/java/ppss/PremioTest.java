package ppss;

import org.easymock.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class PremioTest {
    IMocksControl control;
    ClienteWebService clienteMock;
    Premio premioTestable;

    @BeforeEach
    void setUp(){
        // Control ya que hay varias dependecias
        control = EasyMock.createStrictControl();

        // Dependencia externa
        clienteMock = control.createMock(ClienteWebService.class);

        // Dependencia local
        premioTestable = EasyMock.partialMockBuilder(Premio.class)
                .addMockedMethod("generaNumero")
                .mock(control);

        premioTestable.cliente = clienteMock;
    }

    @Test
    void C1(){
        float numeroAleatorio = 0.07f;
        String premioDeseado = "entrada Final Champions";
        String mensajeEsperado = "Premiado con " + premioDeseado;


        assertDoesNotThrow(
                ()-> {
                    EasyMock.expect(premioTestable.generaNumero()).andReturn(numeroAleatorio);
                    EasyMock.expect(clienteMock.obtenerPremio()).andReturn(premioDeseado);
                }
        );

        control.replay();

        assertEquals(premioTestable.compruebaPremio(), mensajeEsperado);

        control.verify();

    }

    @Test
    void C2(){
        float numeroAleatorio = 0.05f;
        //String premioDeseado = "entrada Final Champions";
        String mensajeEsperado = "No se ha podido obtener el premio";


        assertDoesNotThrow(
                ()-> {
                    EasyMock.expect(premioTestable.generaNumero()).andReturn(numeroAleatorio);
                    EasyMock.expect(clienteMock.obtenerPremio()).andThrow(new ClienteWebServiceException());
                });

        control.replay();

        assertEquals(premioTestable.compruebaPremio(), mensajeEsperado);

        control.verify();
    }

    @Test
    void C3(){
        float numeroAleatorio = 0.48f;
        //String premioDeseado = "entrada Final Champions";
        String mensajeEsperado = "Sin premio";


        EasyMock.expect(premioTestable.generaNumero()).andReturn(numeroAleatorio);
        //assertDoesNotThrow(
        //        ()-> EasyMock.expect(clienteMock.obtenerPremio()).andThrow(new ClienteWebServiceException()));

        control.replay();

        assertEquals(premioTestable.compruebaPremio(), mensajeEsperado);

        control.verify();
    }
}