package ppss;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FicheroTextoTest {
    @Test
    @DisplayName("C1: Debe lanzar FicheroException cuando el fichero no existe")
    void C1_contarCaracteres_should_return_Exception_when_file_does_not_exist(){
        FicheroTexto ficheroTexto = new FicheroTexto();

        FicheroException excepcion = assertThrows(FicheroException.class, () -> ficheroTexto.contarCaracteres("ficheroC1.txt"));

        assertEquals("ficheroC1.txt (No existe el archivo o el directorio)", excepcion.getMessage());
    }

    @Test
    @DisplayName("C2: Debe devolver 3 cuando el archivo tiene 3 caracteres")
    void C2_contarCaracteres_should_return_3_when_file_has_3_chars(){
        FicheroTexto ficheroTexto = new FicheroTexto();

        int resultado = assertDoesNotThrow(() -> ficheroTexto.contarCaracteres("src/test/resources/ficheroCorrecto.txt"));

        assertEquals(3, resultado);
    }


}