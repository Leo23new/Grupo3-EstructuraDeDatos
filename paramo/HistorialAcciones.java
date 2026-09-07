package paramo;

/**
 * HistorialAcciones: implementa deshacer/rehacer usando dos pilas.
 *
 * - pilaDeshacer guarda las acciones ya ejecutadas, en orden LIFO.
 * - pilaRehacer guarda las acciones deshechas, disponibles para
 *   volver a aplicarse mientras el jugador no realice una acción nueva
 *   (al ejecutar un comando nuevo, la pila de rehacer se invalida,
 *   igual que en cualquier editor de texto real).
 */
public class HistorialAcciones {

    private final PilaGenerica<ComandoTorre> pilaDeshacer;
    private final PilaGenerica<ComandoTorre> pilaRehacer;

    public HistorialAcciones() {
        this.pilaDeshacer = new PilaGenerica<>();
        this.pilaRehacer = new PilaGenerica<>();
    }

    /** Ejecuta un comando nuevo (colocación o mejora) y lo registra en el historial. */
    public void ejecutar(ComandoTorre comando) {
        comando.ejecutar();
        pilaDeshacer.apilar(comando);
        pilaRehacer.vaciar();
    }

    public boolean puedeDeshacer() {
        return !pilaDeshacer.estaVacia();
    }

    public boolean puedeRehacer() {
        return !pilaRehacer.estaVacia();
    }

    /** Deshace la última acción realizada (pop de pilaDeshacer). */
    public String deshacer() {
        if (!puedeDeshacer()) {
            return null;
        }
        ComandoTorre comando = pilaDeshacer.desapilar();
        comando.deshacer();
        pilaRehacer.apilar(comando);
        return comando.descripcion();
    }

    /** Vuelve a aplicar la última acción deshecha (pop de pilaRehacer). */
    public String rehacer() {
        if (!puedeRehacer()) {
            return null;
        }
        ComandoTorre comando = pilaRehacer.desapilar();
        comando.ejecutar();
        pilaDeshacer.apilar(comando);
        return comando.descripcion();
    }
}
