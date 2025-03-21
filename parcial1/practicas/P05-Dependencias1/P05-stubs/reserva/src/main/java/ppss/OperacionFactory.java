package ppss;

public class OperacionFactory {
    private static IOperacionBO operacionBO;

    public static IOperacionBO create(){
        if (operacionBO == null){
            return new Operacion();
        } else {
            return operacionBO;
        }
    }

    public void setOperacionBO(IOperacionBO operacionBO){
        OperacionFactory.operacionBO = operacionBO;
    }

}
