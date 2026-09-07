package paramo;

/** Torre de Fuego: daño moderado, buen alcance, ideal contra grupos. */
public class TorreDeFuego extends Torre {
    public TorreDeFuego(int fila, int columna) {
        super("Torre de Fuego", 2, 18, 75, fila, columna);
    }

    @Override
    public int getCostoMejora() {
        return 45 * nivel;
    }
}
