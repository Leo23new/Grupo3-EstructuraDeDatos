package paramo;

import java.util.List;
import java.util.Scanner;

/**
 * Juego: controlador principal. Muestra el mapa, gestiona el menú de
 * consola (colocar/mejorar torres, deshacer/rehacer, avanzar
 * quantum) y ejecuta el ciclo de vida completo de la partida.
 */
public class Juego {

    private final Mapa mapa;
    private final GestorOleadas gestorOleadas;
    private final HistorialAcciones historial;
    private final Scanner sc;

    private SimuladorRuta simulador;
    private Oleada oleadaActual;
    private int vidaJugador;
    private int puntuacion;
    private int oro;
    private boolean juegoTerminado;

    public Juego(Mapa mapa, int totalOleadas, int vidaInicial, int oroInicial, Scanner sc) {
        this.mapa = mapa;
        this.gestorOleadas = new GestorOleadas(totalOleadas);
        this.historial = new HistorialAcciones();
        this.sc = sc;
        this.simulador = new SimuladorRuta(mapa);
        this.vidaJugador = vidaInicial;
        this.oro = oroInicial;
        this.puntuacion = 0;
        this.juegoTerminado = false;
    }

    public void iniciar() {
        mostrarEncabezado();
        while (!juegoTerminado) {
            mostrarEstado();
            mostrarMenu();
            String opcion = sc.nextLine().trim();
            procesarOpcion(opcion);
        }
    }

    private void mostrarEncabezado() {
        System.out.println("==================================================");
        System.out.println(" DEFENSA DEL PARAMO: Cozy y la Fuente de Calor Ancestral");
        System.out.println(" Mapa: " + mapa.getNombre());
        System.out.println("==================================================\n");
    }

    private void mostrarEstado() {
        List<Cozy> cozysEnPantalla = simulador.snapshotCozys();
        int[] posiciones = new int[cozysEnPantalla.size()];
        for (int i = 0; i < cozysEnPantalla.size(); i++) {
            posiciones[i] = cozysEnPantalla.get(i).getPosicionEnRuta();
        }

        System.out.println("\n--- ESTADO ---");
        System.out.printf("Vida: %d | Oro: %d | Oleada: %d/%d | Puntuacion: %d%n",
                vidaJugador, oro, gestorOleadas.getOleadasGeneradas(), gestorOleadas.getTotalOleadas(), puntuacion);
        System.out.println(mapa.dibujar(cozysEnPantalla, posiciones));
        System.out.println("Torres colocadas: " + mapa.getTorresColocadas().size()
                + " | Cozy en ruta: " + simulador.cantidadCozysEnRuta());
    }

    private void mostrarMenu() {
        System.out.println("\n1. Colocar torre");
        System.out.println("2. Mejorar torre");
        System.out.println("3. Deshacer (undo)");
        System.out.println("4. Rehacer (redo)");
        System.out.println("5. Avanzar siguiente quantum");
        System.out.println("6. Ver catalogo de torres");
        System.out.println("0. Salir");
        System.out.print("Elige una opcion: ");
    }

    private void procesarOpcion(String opcion) {
        switch (opcion) {
            case "1": colocarTorre(); break;
            case "2": mejorarTorre(); break;
            case "3": deshacer(); break;
            case "4": rehacer(); break;
            case "5": avanzarQuantum(); break;
            case "6": mostrarCatalogo(); break;
            case "0": juegoTerminado = true; System.out.println("Partida finalizada por el jugador."); break;
            default: System.out.println("Opcion invalida.");
        }
    }

    private void mostrarCatalogo() {
        System.out.println("\n--- CATALOGO DE TORRES ---");
        System.out.println("1. Torre de Piedra Kañari - Costo 50 - Alto danio, alcance corto");
        System.out.println("2. Torre de Fuego         - Costo 75 - Danio moderado, buen alcance");
        System.out.println("3. Torre de Viento        - Costo 60 - Bajo danio, mayor alcance");
    }

    private void colocarTorre() {
        mostrarCatalogo();
        System.out.print("Tipo de torre (1-3): ");
        String tipo = sc.nextLine().trim();
        System.out.print("Fila: ");
        int fila = leerEntero();
        System.out.print("Columna: ");
        int columna = leerEntero();

        if (!mapa.esPosicionValida(fila, columna)) {
            System.out.println("Posicion invalida: fuera del mapa, sobre la ruta, u ocupada.");
            return;
        }

        Torre torre;
        switch (tipo) {
            case "1": torre = new TorrePiedraKanari(fila, columna); break;
            case "2": torre = new TorreDeFuego(fila, columna); break;
            case "3": torre = new TorreDeViento(fila, columna); break;
            default: System.out.println("Tipo invalido."); return;
        }

        if (oro < torre.getCosto()) {
            System.out.println("Oro insuficiente. Necesitas " + torre.getCosto() + ", tienes " + oro + ".");
            return;
        }

        oro -= torre.getCosto();
        historial.ejecutar(new ComandoColocarTorre(mapa, torre));
        System.out.println("Torre colocada: " + torre);
    }

    private void mejorarTorre() {
        List<Torre> torres = mapa.getTorresColocadas();
        if (torres.isEmpty()) {
            System.out.println("No hay torres colocadas todavia.");
            return;
        }
        for (int i = 0; i < torres.size(); i++) {
            System.out.println((i + 1) + ". " + torres.get(i));
        }
        System.out.print("Elige el numero de torre a mejorar: ");
        int indice = leerEntero() - 1;
        if (indice < 0 || indice >= torres.size()) {
            System.out.println("Indice invalido.");
            return;
        }
        Torre torre = torres.get(indice);
        int costoMejora = torre.getCostoMejora();
        if (oro < costoMejora) {
            System.out.println("Oro insuficiente para mejorar. Necesitas " + costoMejora + ".");
            return;
        }
        oro -= costoMejora;
        historial.ejecutar(new ComandoMejorarTorre(torre));
        System.out.println("Torre mejorada: " + torre);
    }

    private void deshacer() {
        String descripcion = historial.deshacer();
        if (descripcion == null) {
            System.out.println("No hay acciones para deshacer.");
        } else {
            System.out.println("Deshecho: " + descripcion);
        }
    }

    private void rehacer() {
        String descripcion = historial.rehacer();
        if (descripcion == null) {
            System.out.println("No hay acciones para rehacer.");
        } else {
            System.out.println("Rehecho: " + descripcion);
        }
    }

    private void avanzarQuantum() {
        if (simulador.hayCozysEnRuta()) {
            SimuladorRuta.ResultadoQuantum resultado = simulador.avanzarQuantum();
            puntuacion += resultado.puntosGanados;
            vidaJugador -= resultado.vidaPerdida;
            oro += resultado.puntosGanados / 2;
            for (String evento : resultado.eventos) {
                System.out.println("  > " + evento);
            }
        } else if (gestorOleadas.hayOleadasPendientes()) {
            oleadaActual = gestorOleadas.siguienteOleada();
            for (Cozy cozy : oleadaActual.getCozys()) {
                simulador.encolarCozy(cozy);
            }
            System.out.println("Comienza la oleada " + oleadaActual.getNumero()
                    + " con " + oleadaActual.getCozys().size() + " Cozy.");
        } else {
            System.out.println("¡Victoria! Sobreviviste todas las oleadas del páramo.");
            juegoTerminado = true;
            return;
        }

        if (vidaJugador <= 0) {
            System.out.println("La Fuente de Calor Ancestral se ha apagado. Fin del juego.");
            juegoTerminado = true;
        }
    }

    private int leerEntero() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
