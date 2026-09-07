package paramo;

public class ComandoColocarTorre implements ComandoTorre {

    private final Mapa mapa;
    private final Torre torre;

    public ComandoColocarTorre(Mapa mapa, Torre torre) {
        this.mapa = mapa;
        this.torre = torre;
    }

    @Override
    public void ejecutar() {
        mapa.agregarTorre(torre);
    }

    @Override
    public void deshacer() {
        mapa.quitarTorre(torre);
    }

    @Override
    public String descripcion() {
        return "Colocar " + torre.getNombre() + " en (" + torre.getFila() + "," + torre.getColumna() + ")";
    }

    public Torre getTorre() {
        return torre;
    }
}
