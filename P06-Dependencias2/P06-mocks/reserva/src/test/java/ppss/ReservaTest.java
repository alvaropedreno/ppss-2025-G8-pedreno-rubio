package ppss;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ppss.excepciones.IsbnInvalidoException;
import ppss.excepciones.JDBCException;
import ppss.excepciones.ReservaException;
import ppss.excepciones.SocioInvalidoException;

import static org.junit.jupiter.api.Assertions.*;

class ReservaTest {
    final String  validLogin      = "ppss";
    final String invalidLogin      = "xxxx";
    final String  validPassword   = "ppss";
    final String invalidPassword  = "xxxx";
    final String  validPartner    = "Pepe";
    final String  invalidPartner  = "Luis";
    final String  validISBNs[]    = {"22222", "33333"};
    final Usuario adminUser       = Usuario.BIBLIOTECARIO;

    private IMocksControl control;
    private Reserva reservaTestable;
    private FactoriaBOs factoriaBOsMock;
    private IOperacionBO operacionBOMock;

    @BeforeEach
    void setUp() {
        //Partial Mocking
        control = EasyMock.createStrictControl();
        reservaTestable = EasyMock.partialMockBuilder(Reserva.class)
                .addMockedMethod("compruebaPermisos")
                .mock(control);

        factoriaBOsMock = control.createMock(FactoriaBOs.class);
        operacionBOMock = control.createMock(IOperacionBO.class);

        reservaTestable.setFactoria(factoriaBOsMock);
    }

    @Test
    void C1_should_return_exception_when_login_and_password_invalid(){
        //Definición de comporamiento
        String login = invalidLogin;
        String password = invalidPassword;
        String socio = validPartner;
        String mensajeEsperado = "ERROR de permisos; ";
        String isbn[] = {validISBNs[1]};

        //Configuración de los mocks
        EasyMock.expect(reservaTestable.compruebaPermisos(login, password, Usuario.BIBLIOTECARIO)).andReturn(false);

        //Activación
        control.replay();

        //Ejecutar
        ReservaException exception = assertThrows(ReservaException.class, ()-> reservaTestable.realizaReserva(login, password, socio, isbn));
        assertEquals(exception.getMessage(), mensajeEsperado);

        control.verify();
    }

    @Test
    void C2_should_not_return_exception_when_all_data_is_valid(){
        //Definición de comportamiento
        String login = validLogin;
        String password = validPassword;
        String socio = validPartner;
        String isbns[] = validISBNs;

        //Configuración de los mocks
        assertDoesNotThrow(()-> EasyMock.expect(reservaTestable.compruebaPermisos(login, password, Usuario.BIBLIOTECARIO)).andReturn(true));
        EasyMock.expect(factoriaBOsMock.getOperacionBO()).andReturn(operacionBOMock);

        assertDoesNotThrow(()->{
            operacionBOMock.operacionReserva(socio,isbns[0]);
            operacionBOMock.operacionReserva(socio,isbns[1]);
        });

        //Activación
        control.replay();

        //Ejecutar
        assertDoesNotThrow(()-> reservaTestable.realizaReserva(login,password,socio,isbns));

        control.verify();
    }

    @Test
    void C3_should_return_exception_when_2_isbn_are_not_valid(){
        //Definición de comportamiento
        String login = validLogin;
        String password = validPassword;
        String socio = validPartner;
        String isbns[] = {"11111", validISBNs[0], "55555"};
        String mensajeEsperado = "ISBN invalido:11111; ISBN invalido:55555; ";

        //Configuración de los mocks
        assertDoesNotThrow(()-> EasyMock.expect(reservaTestable.compruebaPermisos(login, password, Usuario.BIBLIOTECARIO)).andReturn(true));
        EasyMock.expect(factoriaBOsMock.getOperacionBO()).andReturn(operacionBOMock);

        assertDoesNotThrow(()-> {
            operacionBOMock.operacionReserva(socio, isbns[0]);
            EasyMock.expectLastCall().andThrow(new IsbnInvalidoException());
            operacionBOMock.operacionReserva(socio, isbns[1]);
            operacionBOMock.operacionReserva(socio, isbns[2]);
            EasyMock.expectLastCall().andThrow(new IsbnInvalidoException());
        });


        //Activación
        control.replay();

        //Ejecutar
        ReservaException excepcion = assertThrows(ReservaException.class, ()-> reservaTestable.realizaReserva(login,password,socio,isbns));
        assertEquals(mensajeEsperado, excepcion.getMessage());

        control.verify();
    }

    @Test
    void C4_should_return_exception_when_partner_not_valid(){
        //Definición de comportamiento
        String login = validLogin;
        String password = validPassword;
        String socio = invalidPartner;
        String isbns[] = {"22222"};
        String mensajeEsperado = "SOCIO invalido; ";

        //Configuración de los mocks
        assertDoesNotThrow(()-> EasyMock.expect(reservaTestable.compruebaPermisos(login, password, Usuario.BIBLIOTECARIO)).andReturn(true));
        EasyMock.expect(factoriaBOsMock.getOperacionBO()).andReturn(operacionBOMock);

        assertDoesNotThrow(()-> {
            operacionBOMock.operacionReserva(socio, isbns[0]);
            EasyMock.expectLastCall().andThrow(new SocioInvalidoException());
        });


        //Activación
        control.replay();

        //Ejecutar
        ReservaException excepcion = assertThrows(ReservaException.class, ()-> reservaTestable.realizaReserva(login,password,socio,isbns));
        assertEquals(mensajeEsperado, excepcion.getMessage());

        control.verify();
    }

    @Test
    void C5_should_return_exception_when_some_data_is_invalid(){
        //Definición de comportamiento
        String login = validLogin;
        String password = validPassword;
        String socio = validPartner;
        String isbns[] = {"11111", "22222", "33333"};
        String mensajeEsperado = "ISBN invalido:11111; CONEXION invalida; ";

        //Configuración de los mocks
        assertDoesNotThrow(()-> EasyMock.expect(reservaTestable.compruebaPermisos(login, password, Usuario.BIBLIOTECARIO)).andReturn(true));
        EasyMock.expect(factoriaBOsMock.getOperacionBO()).andReturn(operacionBOMock);

        assertDoesNotThrow(()-> {
            operacionBOMock.operacionReserva(socio, isbns[0]);
            EasyMock.expectLastCall().andThrow(new IsbnInvalidoException());
            operacionBOMock.operacionReserva(socio, isbns[1]);
            operacionBOMock.operacionReserva(socio, isbns[2]);
            EasyMock.expectLastCall().andThrow(new JDBCException());
        });


        //Activación
        control.replay();

        //Ejecutar
        ReservaException excepcion = assertThrows(ReservaException.class, ()-> reservaTestable.realizaReserva(login,password,socio,isbns));
        assertEquals(mensajeEsperado, excepcion.getMessage());

        control.verify();
    }
}