package ppss;

public class MatriculaAlumnoTestable extends MatriculaAlumno {
    OperacionStub operacionStub;

    @Override
    public Operacion getOperacion() {
        return operacionStub;
    }

    public void setOperacionStub(OperacionStub operacionStub) {
        this.operacionStub = operacionStub;
    }
}
