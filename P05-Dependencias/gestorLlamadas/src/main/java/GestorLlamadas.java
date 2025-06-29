import java.util.Calendar;

public class GestorLlamadas {

    private static final double TARIFA_NOCTURNA=12.2;

    private static final double TARIFA_DIURNA=20.7;

    public int getHoraActual(){
        Calendar c = Calendar.getInstance();
        int hora = c.get(Calendar.HOUR);
        return hora;
    }

    public double calculaConsumo(int minutos){
        int hora = getHoraActual(); //Dependencia Externa
        if (hora < 8 || hora > 20){
            return minutos * TARIFA_NOCTURNA;
        }
        else{
            return minutos * TARIFA_DIURNA;
        }
    }

}
