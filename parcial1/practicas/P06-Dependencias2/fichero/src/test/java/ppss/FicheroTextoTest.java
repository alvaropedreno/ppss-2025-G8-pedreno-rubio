package ppss;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.jupiter.api.*;

import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FicheroTextoTest {
    IMocksControl control;
    FileReader fileReaderMock;
    FicheroTexto ficheroTextoTestable;

    @BeforeEach
    void setUp() {
        // Control ya que hay varias dependecias
        IMocksControl control = EasyMock.createStrictControl();

        // Dependencia externa
        fileReaderMock = EasyMock.createMock(FileReader.class);

        // Dependencia locales
        ficheroTextoTestable = EasyMock.partialMockBuilder(FicheroTexto.class)
                .addMockedMethod("getFileReader")
                .mock(control);
    }
    @Test
    void C1(){
        String pathFichero = "src/test/resources/ficheroC1.txt";
        String salidaEsperada = pathFichero + " (Error al leer el archivo)";

        assertDoesNotThrow(
        () ->
                {
                    EasyMock.expect(ficheroTextoTestable.getFichero(pathFichero)).andReturn(fileReaderMock);
                }
        );
        EasyMock.expect(fileReaderMock.read())
    }

}