package paramo;

/**
 * Cozy: criatura del páramo que avanza por la ruta buscando robar
 * el calor de la Fuente Ancestral. Es la entidad "enemigo" del juego
 * (nombre obligatorio según el enunciado del caso de estudio).
 */
public class Cozy {

    private final String tipo;
    private int pv;
    private final int pvMax;
    private final int danioAlJugador;
    private final int recompensa;
    private int posicionEnRuta;

    public Cozy(String tipo, int pv, int danioAlJugador, int recompensa) {
        this.tipo = tipo;
        this.pv = pv;
        this.pvMax = pv;
        this.danioAlJugador = danioAlJugador;
        this.recompensa = recompensa;
        this.posicionEnRuta = 0;
    }

    public void recibirDanio(int dd) {
        pv -= dd;
        if (pv < 0) {
            pv = 0;
        }
    }

    public boolean estaVivo() {
        return pv > 0;
    }

    public void avanzar() {
        posicionEnRuta++;
    }

    public String getTipo() {
        return tipo;
    }

    public int getPv() {
        return pv;
    }

    public int getPvMax() {
        return pvMax;
    }

    public int getDanioAlJugador() {
        return danioAlJugador;
    }

    public int getRecompensa() {
        return recompensa;
    }

    public int getPosicionEnRuta() {
        return posicionEnRuta;
    }

    @Override
    public String toString() {
        return String.format("Cozy[%s] PV=%d/%d pos=%d", tipo, pv, pvMax, posicionEnRuta);
    }
}
