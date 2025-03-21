package ppss;

import java.util.ArrayList;

public class OperacionStub extends Operacion {
    ArrayList<String> invalidas;
    ArrayList<String> cursadas;

    public void setValidas(ArrayList<String> invalidas) {
        this.invalidas = invalidas;
    }

    public void setCursadas(ArrayList<String> cursadas) {
        this.cursadas = cursadas;
    }

    @Override
    public void compruebaMatricula(String dni, String asignatura) throws AsignaturaCursadaException, AsignaturaIncorrectaException {
        if (cursadas.contains(asignatura)){
            throw new AsignaturaCursadaException("lo que as");
        }

        if (invalidas.contains(asignatura)){
            throw new AsignaturaIncorrectaException("lo que as");
        }
    }

}
