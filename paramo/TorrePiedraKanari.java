package paramo;

/** Torre de Piedra Kañari: alto daño, alcance corto, la más económica. */
public class TorrePiedraKanari extends Torre {
    public TorrePiedraKanari(int fila, int columna) {
        super("Torre de Piedra Kañari", 1, 25, 50, fila, columna);
    }

    @Override
    public int getCostoMejora() {
        return 30 * nivel;
    }
}
