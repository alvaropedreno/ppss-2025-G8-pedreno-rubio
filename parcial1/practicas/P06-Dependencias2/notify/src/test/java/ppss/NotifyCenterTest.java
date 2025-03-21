package ppss;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class NotifyCenterTest {
    IMocksControl control;
    NotifyCenter notifyCenterMock;
    MailServer mailServerMock;

    @BeforeEach
    void setUp(){
        control = EasyMock.createStrictControl();

        mailServerMock = control.createMock(MailServer.class);

        notifyCenterMock = EasyMock.partialMockBuilder(NotifyCenter.class)
                .addMockedMethod("getServer")
                .addMockedMethod("sendNotify")
                .addMockedMethod("getFechaActual")
                .mock(control);
    }

    @Test
    void C1(){
        LocalDate fecha = LocalDate.of(2025, 3, 11);
        ArrayList<String> emails = new ArrayList<String>(Arrays.asList("email1", "email2", "email3", "email4"));
        EasyMock.expect(mailServerMock.findMailItemsWithDate(fecha)).andReturn(emails);
        EasyMock.expect(notifyCenterMock.getServer()).andReturn(mailServerMock);
        EasyMock.expect(notifyCenterMock.getFechaActual()).andReturn(fecha);

        control.replay();

        assertDoesNotThrow(()->{
            notifyCenterMock.sendNotify("email1");
            notifyCenterMock.sendNotify("email2");
            EasyMock.expectLastCall().andThrow(new FailedNotifyException());
            notifyCenterMock.sendNotify("email3");
            EasyMock.expectLastCall().andThrow(new FailedNotifyException());
            notifyCenterMock.sendNotify("email4");
        });

        control.verify();

        FailedNotifyException excepcion = assertThrows(FailedNotifyException.class, ()->{notifyCenterMock.notifyUsers(fecha);});
        assertEquals(excepcion.getMessage(), "Failures during sending process");

        control.replay();
    }

}