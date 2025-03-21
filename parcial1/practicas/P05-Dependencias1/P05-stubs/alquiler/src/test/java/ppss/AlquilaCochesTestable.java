package ppss;

public class AlquilaCochesTestable extends AlquilaCoches {
    IService servicio;

    public void setServicio(IService servicio) {
        this.servicio = servicio;
    }

    @Override
    public IService getServicio() {
        return servicio;
    }

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }
}
