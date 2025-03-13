package ppss;

import org.easymock.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PremioTest {

    // Necesitamos partial mocking ya que se requiere probar un
    // metodo que hace llamadas internas a metodos de la misma clase
    @Test
    void C1_should_return_premio_champions_when_random_number_is_0_07(){
        //Partial Mocking
        IMocksControl control = EasyMock.createStrictControl();
        Premio premioTestable = EasyMock.partialMockBuilder(Premio.class)
                .addMockedMethod("generaNumero")
                .mock(control);

        //Mock ClienteWebService para la dependencia externa
        ClienteWebService clienteMock = control.mock(ClienteWebService.class);
        premioTestable.cliente = clienteMock;

        //Definición de comporamiento
        float generaNumeroEsperado = 0.07f;
        String obtenerPremioEsperado = "entrada final Champions";
        String resultadoCompruebaPremioEsperado = "Premiado con entrada final Champions";

        //Configuración de los mocks
        EasyMock.expect(premioTestable.generaNumero()).andReturn(generaNumeroEsperado);
        assertDoesNotThrow(()->EasyMock.expect(clienteMock.obtenerPremio()).andReturn(obtenerPremioEsperado));

        //Activación
        control.replay();

        //Ejecutar
        assertEquals(premioTestable.compruebaPremio(), resultadoCompruebaPremioEsperado);

        //Verificar que todo se llamo correctamente
        control.verify();
    }

    // Necesitamos partial mocking ya que se requiere probar un
    // metodo que hace llamadas internas a metodos de la misma clase
    @Test
    void C2_should_return_ClienteWebServiceException_when_random_number_is_0_05(){
        //Partial Mocking
        IMocksControl control = EasyMock.createStrictControl();
        Premio premioTestable = EasyMock.partialMockBuilder(Premio.class)
                .addMockedMethod("generaNumero")
                .mock(control);

        //Mock ClienteWebService para la dependencia externa
        ClienteWebService clienteMock = control.mock(ClienteWebService.class);
        premioTestable.cliente = clienteMock;

        //Definición de comporamiento
        float generaNumeroEsperado = 0.05f;
        String obtenerPremioEsperado = "entrada final Champions";
        String resultadoCompruebaPremioEsperado = "No se ha podido obtener el premio";

        //Configuración de los mocks
        EasyMock.expect(premioTestable.generaNumero()).andReturn(generaNumeroEsperado);
        assertDoesNotThrow(()->EasyMock.expect(clienteMock.obtenerPremio()).andThrow(new ClienteWebServiceException()));

        //Activación
        control.replay();

        //Ejecutar
        assertEquals(premioTestable.compruebaPremio(), resultadoCompruebaPremioEsperado);

        //Verificar que todo se llamo correctamente
        control.verify();
    }

    @Test
    void C3_should_return_sin_premio_when_random_number_is_0_48(){
        //Partial Mocking
        Premio premioTestable = EasyMock.partialMockBuilder(Premio.class)
                .addMockedMethod("generaNumero")
                .createMock();

        //Mock ClienteWebService para la dependencia externa
        //Aqui no hace falta, ya que el método nunca llega a llamar a cliente.obtenerPremio()
        //ClienteWebService clienteMock = control.mock(ClienteWebService.class);
        //premioTestable.cliente = clienteMock;

        //Definición de comporamiento
        float generaNumeroEsperado = 0.48f;
        String obtenerPremioEsperado = "entrada final Champions";
        String resultadoCompruebaPremioEsperado = "Sin premio";

        //Configuración de los mocks
        EasyMock.expect(premioTestable.generaNumero()).andReturn(generaNumeroEsperado);
        //assertDoesNotThrow(()->EasyMock.expect(clienteMock.obtenerPremio()).andReturn(obtenerPremioEsperado));

        //Activación
        EasyMock.replay(premioTestable);

        //Ejecutar
        assertEquals(premioTestable.compruebaPremio(), resultadoCompruebaPremioEsperado);

        //Verificar que todo se llamo correctamente
        EasyMock.verify(premioTestable);
    }
}