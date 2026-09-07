package paramo;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapa: escenario del páramo con su ruta predefinida, la Fuente de
 * Calor Ancestral a proteger y las torres colocadas por el jugador.
 * Soporta múltiples mapas mediante la clase MapaFactory (lista
 * secuencial de configuraciones de mapa).
 */
public class Mapa {

    private final String nombre;
    private final int filas;
    private final int columnas;
    private final Ruta ruta;
    private final List<Torre> torresColocadas;

    public Mapa(String nombre, int filas, int columnas, Ruta ruta) {
        this.nombre = nombre;
        this.filas = filas;
        this.columnas = columnas;
        this.ruta = ruta;
        this.torresColocadas = new ArrayList<>();
    }

    /** Verifica que la casilla exista, no esté sobre la ruta y esté libre. */
    public boolean esPosicionValida(int fila, int columna) {
        if (fila < 0 || fila >= filas || columna < 0 || columna >= columnas) {
            return false;
        }
        for (Ruta.Casilla c : ruta.getCasillas()) {
            if (c.fila == fila && c.columna == columna) {
                return false;
            }
        }
        for (Torre t : torresColocadas) {
            if (t.getFila() == fila && t.getColumna() == columna) {
                return false;
            }
        }
        return true;
    }

    public void agregarTorre(Torre torre) {
        torresColocadas.add(torre);
    }

    public void quitarTorre(Torre torre) {
        torresColocadas.remove(torre);
    }

    public List<Torre> getTorresColocadas() {
        return torresColocadas;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public String getNombre() {
        return nombre;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    /** Dibuja el mapa en ASCII para la interfaz de consola. */
    public String dibujar(List<Cozy> cozysEnPantalla, int[] posicionesCozy) {
        char[][] grilla = new char[filas][columnas];
        for (char[] fila : grilla) {
            java.util.Arrays.fill(fila, '.');
        }
        for (Ruta.Casilla c : ruta.getCasillas()) {
            grilla[c.fila][c.columna] = ':';
        }
        Ruta.Casilla destino = ruta.getCasilla(ruta.longitud() - 1);
        grilla[destino.fila][destino.columna] = 'X';

        for (Torre t : torresColocadas) {
            grilla[t.getFila()][t.getColumna()] = 'T';
        }

        for (int i = 0; i < cozysEnPantalla.size(); i++) {
            int posRuta = posicionesCozy[i];
            if (posRuta >= 0 && posRuta < ruta.longitud()) {
                Ruta.Casilla c = ruta.getCasilla(posRuta);
                grilla[c.fila][c.columna] = 'C';
            }
        }

        StringBuilder sb = new StringBuilder();
        for (char[] fila : grilla) {
            sb.append(new String(fila)).append('\n');
        }
        return sb.toString();
    }
}
