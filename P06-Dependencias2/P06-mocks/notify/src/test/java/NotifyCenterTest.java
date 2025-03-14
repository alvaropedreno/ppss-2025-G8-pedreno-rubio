import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ppss.MailServer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class NotifyCenterTest {
    private NotifyCenter notify;
    private IMocksControl control;
    private MailServer mockServer;

    @BeforeEach
    public void init(){
        //Partial Mocking
        control = EasyMock.createStrictControl();
        notify = EasyMock.partialMockBuilder(NotifyCenter.class)
                .addMockedMethods("getServer", "sendNotify", "getFechaActual")
                .createMock(control);

        //Mock MailServer para la dependencia externa
        mockServer = control.createMock(MailServer.class);
    }

    @Test
    void C1_should_fail_when_server_does_not_sent_two_emails(){
        //Definición de comporamiento
        LocalDate fecha = LocalDate.of(2025, 3, 11);
        ArrayList<String> emails = new ArrayList<String>(Arrays.asList("email1", "email2", "email3", "email4"));
        String excepcionEsperada = "Failures during sending process";

        //Configuración de los mocks
        EasyMock.expect(notify.getServer()).andReturn(mockServer);
        EasyMock.expect(notify.getFechaActual()).andReturn(fecha);
        EasyMock.expect(mockServer.findMailItemsWithDate(fecha)).andReturn(emails);

        assertDoesNotThrow(()-> {
            notify.sendNotify("email1"); // No lanza excepción
            notify.sendNotify("email2"); // Lanzará excepción
            EasyMock.expectLastCall().andThrow(new FailedNotifyException());
            notify.sendNotify("email3"); // Lanzará excepción
            EasyMock.expectLastCall().andThrow(new FailedNotifyException());
            notify.sendNotify("email4"); // No lanza excepción
        });

        //Activación
        control.replay();

        //Ejecutar
        FailedNotifyException excepcion = assertThrows(FailedNotifyException.class, ()->{notify.notifyUsers(fecha);});
        assertEquals(excepcion.getMessage(),excepcionEsperada);

        //
        control.verify();
    }

    @Test
    void C2_should_return_excepcion_when_notify_date_and_today_date_is_different(){
        //Definición de comporamiento
        LocalDate fechaActual = LocalDate.of(2025, 3, 12);
        LocalDate fechaNotify = LocalDate.of(2025, 2, 12);
        String excepcionEsperada = "Date error";

        //Configuración de los mocks
        EasyMock.expect(notify.getServer()).andReturn(mockServer);
        EasyMock.expect(notify.getFechaActual()).andReturn(fechaActual);

        //Activación
        control.replay();

        //Ejecutar
        FailedNotifyException excepcion = assertThrows(FailedNotifyException.class, ()->{notify.notifyUsers(fechaNotify);});
        assertEquals(excepcion.getMessage(),excepcionEsperada);

        //
        control.verify();
    }

    @Test
    void C3_should_do_nothing_when_dates_are_equals_and_not_messages(){
        //Definición de comporamiento
        LocalDate fecha = LocalDate.of(2025, 3, 23);

        //Configuración de los mocks
        EasyMock.expect(notify.getServer()).andReturn(mockServer);
        EasyMock.expect(notify.getFechaActual()).andReturn(fecha);
        EasyMock.expect(mockServer.findMailItemsWithDate(fecha)).andReturn(new ArrayList<>());

        //Activación
        control.replay();

        //Ejecutar
        assertDoesNotThrow(() -> notify.notifyUsers(fecha));

        //Verificar
        control.verify();
    }
}