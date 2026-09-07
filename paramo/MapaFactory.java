package paramo;

import java.util.ArrayList;
import java.util.List;

/**
 * MapaFactory: catálogo de mapas disponibles, guardado en una lista
 * secuencial. Permite que el juego soporte varios escenarios sin
 * cambiar la lógica del resto de clases.
 */
public class MapaFactory {

    public static List<Mapa> construirCatalogo() {
        List<Mapa> catalogo = new ArrayList<>();
        catalogo.add(construirParamoDelChimborazo());
        catalogo.add(construirValleDeQuilotoa());
        return catalogo;
    }

    private static Mapa construirParamoDelChimborazo() {
        Ruta ruta = new Ruta();
        int fila = 0;
        for (int columna = 0; columna < 6; columna++) ruta.agregarCasilla(fila, columna);
        for (fila = 1; fila < 4; fila++) ruta.agregarCasilla(fila, 5);
        for (int columna = 5; columna >= 1; columna--) ruta.agregarCasilla(fila, columna);
        for (fila = 4; fila < 7; fila++) ruta.agregarCasilla(fila, 1);
        for (int columna = 1; columna < 7; columna++) ruta.agregarCasilla(fila, columna);

        return new Mapa("Paramo del Chimborazo", 8, 8, ruta);
    }

    private static Mapa construirValleDeQuilotoa() {
        Ruta ruta = new Ruta();
        int columna = 0;
        for (int fila = 0; fila < 8; fila++) ruta.agregarCasilla(fila, columna);
        int fila = 7;
        for (columna = 1; columna < 5; columna++) ruta.agregarCasilla(fila, columna);
        for (fila = 6; fila >= 0; fila--) ruta.agregarCasilla(fila, 4);
        for (columna = 5; columna < 8; columna++) ruta.agregarCasilla(0, columna);

        return new Mapa("Valle de Quilotoa", 8, 8, ruta);
    }
}
