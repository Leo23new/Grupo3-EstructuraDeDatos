package paramo;

/** Torre de Viento: bajo daño pero el mayor alcance, ideal como soporte. */
public class TorreDeViento extends Torre {
    public TorreDeViento(int fila, int columna) {
        super("Torre de Viento", 3, 10, 60, fila, columna);
    }

    @Override
    public int getCostoMejora() {
        return 35 * nivel;
    }
}
