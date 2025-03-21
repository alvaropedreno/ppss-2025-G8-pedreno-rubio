package ppss;

import ppss.excepciones.IsbnInvalidoException;
import ppss.excepciones.JDBCException;
import ppss.excepciones.SocioInvalidoException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class IOperacionStub implements IOperacionBO{
    boolean SocioExcepcion = false;
    ArrayList<String> isbnInvalidos;
    ArrayList<String> JDBCInvalidos;

    public void setSocioExcepcion(boolean socioExcepcion) {
        this.SocioExcepcion = socioExcepcion;
    }

    public void setIsbnInvalidos(ArrayList<String> isbnInvalidos) {
        this.isbnInvalidos = isbnInvalidos;
    }

    public void setJDBCInvalidos(ArrayList<String> JDBCInvalidos) {
        this.JDBCInvalidos = JDBCInvalidos;
    }

    @Override
    public void operacionReserva(String socio, String isbn) throws IsbnInvalidoException, JDBCException, SocioInvalidoException {
        if (this.SocioExcepcion) {
            throw new SocioInvalidoException();
        }

        if (this.isbnInvalidos.contains(isbn)) {
            throw new SocioInvalidoException();
        }

        if (this.JDBCInvalidos.contains(isbn)) {
            throw new JDBCException();
        }
    }

}
