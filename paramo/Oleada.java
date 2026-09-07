package paramo;

import java.util.ArrayList;
import java.util.List;

/**
 * Oleada: conjunto de Cozy que aparecen juntos en un momento del juego.
 * La dificultad crece de oleada en oleada (más enemigos, más PV).
 */
public class Oleada {

    private final int numero;
    private final List<Cozy> cozys;

    public Oleada(int numero) {
        this.numero = numero;
        this.cozys = new ArrayList<>();
    }

    public void agregarCozy(Cozy cozy) {
        cozys.add(cozy);
    }

    public List<Cozy> getCozys() {
        return cozys;
    }

    public int getNumero() {
        return numero;
    }
}
