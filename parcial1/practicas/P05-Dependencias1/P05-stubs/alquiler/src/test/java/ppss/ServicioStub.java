package ppss;

public class ServicioStub extends Servicio {
    int precio;

    public void setPrecio(float precio) {
        this.precio = (int) precio;
    }

    @Override
    public float consultaPrecio(TipoCoche tipo){
        return precio;
    }
}
