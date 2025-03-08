package ppss;

public class OperacionStub extends Operacion {
    @Override
    public void compruebaMatricula(String dni, String asignatura) throws AsignaturaIncorrectaException, AsignaturaCursadaException {
        if ("YYY".equals(asignatura) || "ZZ".equals(asignatura)) {
            throw new AsignaturaIncorrectaException("La asignatura " + asignatura + " no existe");
        }

        // Comprobamos si la asignatura ya ha sido cursada
        if ("P1".equals(asignatura) || "FC".equals(asignatura) || "FFI".equals(asignatura)) {
            throw new AsignaturaCursadaException("La asignatura " + asignatura + " ya cursada");
        }
    }
}
