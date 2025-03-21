package llamadas;

public class CalendarioStub extends Calendario {
    int hora;

    public void setHora(int hora){
        this.hora = hora;
    }

    @Override
    public int getHoraActual(){
        return hora;
    }
}
