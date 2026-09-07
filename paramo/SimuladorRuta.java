package paramo;

import java.util.ArrayList;
import java.util.List;

/**
 * SimuladorRuta: usa una cola circular para actualizar el estado de
 * cada Cozy (sus PV y su posición) tras cada quantum de tiempo, tal
 * como exige el enunciado. Cada Cozy que llega a PV=0 se retira sin
 * volver a encolarse; cada Cozy que llega al final de la ruta también
 * se retira, pero antes descuenta vida al jugador.
 */
public class SimuladorRuta {

    public static class ResultadoQuantum {
        public int cozysEliminados = 0;
        public int puntosGanados = 0;
        public int vidaPerdida = 0;
        public final List<String> eventos = new ArrayList<>();
    }

    private final ColaCircular<Cozy> colaSimulacion;
    private final Mapa mapa;

    public SimuladorRuta(Mapa mapa) {
        this.colaSimulacion = new ColaCircular<>();
        this.mapa = mapa;
    }

    public void encolarCozy(Cozy cozy) {
        colaSimulacion.encolar(cozy);
    }

    public boolean hayCozysEnRuta() {
        return !colaSimulacion.estaVacia();
    }

    public int cantidadCozysEnRuta() {
        return colaSimulacion.tamanio();
    }

    /** Devuelve los Cozy actualmente en ruta, sin alterar la cola (para dibujar el mapa). */
    public List<Cozy> snapshotCozys() {
        List<Cozy> lista = new ArrayList<>();
        int n = colaSimulacion.tamanio();
        for (int i = 0; i < n; i++) {
            Cozy c = colaSimulacion.desencolar();
            lista.add(c);
            colaSimulacion.encolar(c);
        }
        return lista;
    }

    /**
     * Avanza un quantum de tiempo: recorre exactamente una vez cada
     * Cozy actualmente en la cola circular (round-robin), aplica el
     * daño de las torres en rango, y decide si el Cozy vuelve a
     * encolarse, muere, o llega a la Fuente de Calor.
     */
    public ResultadoQuantum avanzarQuantum() {
        ResultadoQuantum resultado = new ResultadoQuantum();
        int cantidadEsteQuantum = colaSimulacion.tamanio();
        Ruta ruta = mapa.getRuta();

        for (int i = 0; i < cantidadEsteQuantum; i++) {
            Cozy cozy = colaSimulacion.desencolar();

            aplicarDanioDeTorresEnRango(cozy);

            if (!cozy.estaVivo()) {
                resultado.cozysEliminados++;
                resultado.puntosGanados += cozy.getRecompensa();
                resultado.eventos.add(cozy.getTipo() + " eliminado (+" + cozy.getRecompensa() + " pts)");
                continue; // no se re-encola
            }

            cozy.avanzar();

            if (ruta.esFinDeRuta(cozy.getPosicionEnRuta())) {
                resultado.vidaPerdida += cozy.getDanioAlJugador();
                resultado.eventos.add(cozy.getTipo() + " llegó a la Fuente (-" + cozy.getDanioAlJugador() + " vida)");
                continue; // no se re-encola
            }

            colaSimulacion.encolar(cozy); // sigue vivo y en ruta: vuelve al final para el próximo quantum
        }

        return resultado;
    }

    private void aplicarDanioDeTorresEnRango(Cozy cozy) {
        Ruta.Casilla posicionActual = mapa.getRuta().getCasilla(cozy.getPosicionEnRuta());
        for (Torre torre : mapa.getTorresColocadas()) {
            if (torre.estaEnRango(posicionActual.fila, posicionActual.columna)) {
                torre.atacar(cozy);
                if (!cozy.estaVivo()) {
                    return;
                }
            }
        }
    }
}
