package llamadas;

public class GestorLlamadasTestable extends GestorLlamadas{
    Calendario calendario;

    public void setCalendario(Calendario calendario) {
        this.calendario = calendario;
    }

    public Calendario getCalendario(){
        return calendario;
    }
}
