package paramo;

public class ComandoMejorarTorre implements ComandoTorre {

    private final Torre torre;
    private int danioAnterior;
    private int alcanceAnterior;
    private int nivelAnterior;

    public ComandoMejorarTorre(Torre torre) {
        this.torre = torre;
    }

    @Override
    public void ejecutar() {
        // Guarda el estado justo antes de mejorar, para poder revertirlo con precisión.
        danioAnterior = torre.getDanio();
        alcanceAnterior = torre.getAlcance();
        nivelAnterior = torre.getNivel();
        torre.mejorar();
    }

    @Override
    public void deshacer() {
        torre.revertirMejora(danioAnterior, alcanceAnterior, nivelAnterior);
    }

    @Override
    public String descripcion() {
        return "Mejorar " + torre.getNombre() + " a Nv." + (nivelAnterior + 1);
    }
}
