package ppss;

import java.util.ArrayList;

public class JustificanteMatricula {
    private String dni;
    private ArrayList<String> asignaturas;
    private ArrayList<String> errores;

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDni() {
        return dni;
    }

    public void setAsignaturas(ArrayList<String> asignaturas) {
        this.asignaturas = asignaturas;
    }

    public ArrayList<String> getAsignaturas() {
        return asignaturas;
    }

    public void setErrores(ArrayList<String> errores) {
        this.errores = errores;
    }

    public ArrayList<String> getErrores() {
        return errores;
    }
}
