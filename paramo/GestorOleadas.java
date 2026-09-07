package paramo;

/**
 * GestorOleadas: genera un número fijo de oleadas con dificultad
 * creciente y las administra mediante una cola simple (FIFO), tal
 * como pide el enunciado para "gestionar el orden de las oleadas
 * enemigas".
 */
public class GestorOleadas {

    private final ColaSimple<Oleada> colaOleadas;
    private final int totalOleadas;
    private int oleadasGeneradas;

    public GestorOleadas(int totalOleadas) {
        this.totalOleadas = totalOleadas;
        this.colaOleadas = new ColaSimple<>();
        this.oleadasGeneradas = 0;
        generarTodasLasOleadas();
    }

    private void generarTodasLasOleadas() {
        for (int numero = 1; numero <= totalOleadas; numero++) {
            colaOleadas.encolar(generarOleada(numero));
        }
    }

    /** Dificultad creciente: más Cozy y más PV por cada oleada sucesiva. */
    private Oleada generarOleada(int numero) {
        Oleada oleada = new Oleada(numero);
        int cantidadCozys = 4 + numero * 2;
        int pvBase = 30 + numero * 15;
        int danioBase = 5 + numero / 2;
        int recompensaBase = 10 + numero;

        for (int i = 0; i < cantidadCozys; i++) {
            String tipo = (numero % 3 == 0 && i == cantidadCozys - 1) ? "Cozy Robusto" : "Cozy";
            int pv = tipo.equals("Cozy Robusto") ? pvBase * 2 : pvBase;
            oleada.agregarCozy(new Cozy(tipo, pv, danioBase, recompensaBase));
        }
        return oleada;
    }

    public boolean hayOleadasPendientes() {
        return !colaOleadas.estaVacia();
    }

    public Oleada siguienteOleada() {
        oleadasGeneradas++;
        return colaOleadas.desencolar();
    }

    public int getTotalOleadas() {
        return totalOleadas;
    }

    public int getOleadasGeneradas() {
        return oleadasGeneradas;
    }
}
