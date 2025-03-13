package ppss;


import org.easymock.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileReader;
import java.io.IOException;

class FicheroTextoTest {

    @Test
    void C1_should_return_exception_when_open_file(){
        //Partial Mocking
        IMocksControl control = EasyMock.createStrictControl();
        FicheroTexto ficheroTestable = EasyMock.partialMockBuilder(FicheroTexto.class)
                .addMockedMethod("getFileReader")
                .mock(control);

        //Mock FileReader para la dependencia externa
        FileReader fileReaderMock = control.mock(FileReader.class);

        //Definición de comporamiento
        String pathFichero = "src/test/resources/ficheroC1.txt";
        String mensajeEsperado = pathFichero + " (Error al leer el archivo)";

        //Configuración de los mocks
        assertDoesNotThrow(()->EasyMock.expect(ficheroTestable.getFileReader(pathFichero)).andReturn(fileReaderMock));
        assertDoesNotThrow(()->EasyMock.expect(fileReaderMock.read()).andReturn((int) 'a').andReturn((int) 'b').andThrow(new IOException()));

        //Activación
        control.replay();

        //Ejecutar
        FicheroException exception = assertThrows(FicheroException.class, ()->ficheroTestable.contarCaracteres(pathFichero));
        assertEquals(exception.getMessage(), mensajeEsperado);

        //Verificar que todo se llamo correctamente
        control.verify();
    }

    @Test
    void C2_should_return_exception_when_closing_file(){
        //Partial Mocking
        IMocksControl control = EasyMock.createStrictControl();
        FicheroTexto ficheroTestable = EasyMock.partialMockBuilder(FicheroTexto.class)
                .addMockedMethod("getFileReader")
                .mock(control);

        //Mock FileReader para la dependencia externa
        FileReader fileReaderMock = control.mock(FileReader.class);

        //Definición de comporamiento
        String pathFichero = "src/test/resources/ficheroC2.txt";
        String mensajeEsperado = pathFichero + " (Error al cerrar el archivo)";

        //Configuración de los mocks
        assertDoesNotThrow(()->EasyMock.expect(ficheroTestable.getFileReader(pathFichero)).andReturn(fileReaderMock));
        assertDoesNotThrow(()->EasyMock.expect(fileReaderMock.read()).andReturn((int) 'a').andReturn((int) 'b').andReturn((int) 'c').andReturn(-1));
        assertDoesNotThrow(()-> fileReaderMock.close());
        EasyMock.expectLastCall().andThrow(new IOException());

        //Activación
        control.replay();

        //Ejecutar
        FicheroException exception = assertThrows(FicheroException.class, ()->ficheroTestable.contarCaracteres(pathFichero));
        assertEquals(exception.getMessage(), mensajeEsperado);

        //Verificar que todo se llamo correctamente
        control.verify();
    }
}