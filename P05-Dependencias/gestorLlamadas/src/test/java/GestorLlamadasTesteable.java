public class GestorLlamadasTesteable extends GestorLlamadas {
    private int h = 0;

    @Override
    public int getHoraActual() {
        return h;
    }

    public void setHoraActual(int h) {
        this.h = h;
    }
}
