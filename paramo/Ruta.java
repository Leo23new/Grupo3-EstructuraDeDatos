package paramo;

import java.util.ArrayList;
import java.util.List;

/**
 * Ruta: secuencia predefinida de casillas (fila, columna) que los Cozy
 * recorren desde el punto de entrada hasta la Fuente de Calor Ancestral.
 * Se implementa como una lista secuencial, tal como pide el enunciado
 * para los elementos de configuración del juego.
 */
public class Ruta {

    public static class Casilla {
        public final int fila;
        public final int columna;
        public Casilla(int fila, int columna) {
            this.fila = fila;
            this.columna = columna;
        }
    }

    private final List<Casilla> casillas;

    public Ruta() {
        this.casillas = new ArrayList<>();
    }

    public void agregarCasilla(int fila, int columna) {
        casillas.add(new Casilla(fila, columna));
    }

    public int longitud() {
        return casillas.size();
    }

    public Casilla getCasilla(int indice) {
        return casillas.get(indice);
    }

    public boolean esFinDeRuta(int indice) {
        return indice >= casillas.size();
    }

    public List<Casilla> getCasillas() {
        return casillas;
    }
}
