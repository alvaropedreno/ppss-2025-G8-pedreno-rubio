package ppss;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarioStub extends Calendario {
    List<Integer> festivos;
    List<Integer> excepciones;

    public CalendarioStub() {
        festivos = new ArrayList<Integer>();
        excepciones = new ArrayList<Integer>();
    }

    public void setFestivos(List<Integer> festivos) {
        this.festivos = festivos;
    }

    public void setExcepciones(List<Integer> excepciones) {
        this.excepciones = excepciones;
    }
    @Override
    public boolean es_festivo(LocalDate fecha) throws CalendarioException {
        if (festivos.contains(fecha.getDayOfMonth())){
            return true;
        } if (excepciones.contains(fecha.getDayOfMonth())){
            throw new CalendarioException("Algo");
        }
        return false;
    }
}
