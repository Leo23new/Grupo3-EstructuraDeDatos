package paramo;

/**
 * Torre abstracta: guardián ancestral del páramo que ataca a los Cozy
 * dentro de su alcance. Cada subtipo representa un elemento distinto
 * de la cosmovisión andina usada como temática del proyecto.
 */
public abstract class Torre {

    protected String nombre;
    protected int alcance;
    protected int danio;
    protected int costo;
    protected int nivel;
    protected int fila;
    protected int columna;

    public Torre(String nombre, int alcance, int danio, int costo, int fila, int columna) {
        this.nombre = nombre;
        this.alcance = alcance;
        this.danio = danio;
        this.costo = costo;
        this.nivel = 1;
        this.fila = fila;
        this.columna = columna;
    }

    /** Determina si una casilla de la ruta está dentro del alcance de la torre. */
    public boolean estaEnRango(int filaObjetivo, int columnaObjetivo) {
        int distancia = Math.abs(fila - filaObjetivo) + Math.abs(columna - columnaObjetivo);
        return distancia <= alcance;
    }

    public void atacar(Cozy objetivo) {
        objetivo.recibirDanio(danio);
    }

    /** Mejora la torre: aumenta daño y alcance, sube de nivel. */
    public void mejorar() {
        nivel++;
        danio = (int) Math.round(danio * 1.4);
        alcance += 1;
    }

    /** Revierte exactamente la última mejora (usado por el undo). */
    public void revertirMejora(int danioAnterior, int alcanceAnterior, int nivelAnterior) {
        this.danio = danioAnterior;
        this.alcance = alcanceAnterior;
        this.nivel = nivelAnterior;
    }

    public abstract int getCostoMejora();

    public String getNombre() {
        return nombre;
    }

    public int getAlcance() {
        return alcance;
    }

    public int getDanio() {
        return danio;
    }

    public int getCosto() {
        return costo;
    }

    public int getNivel() {
        return nivel;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    @Override
    public String toString() {
        return String.format("%s (Nv.%d) DD=%d Alcance=%d en (%d,%d)",
                nombre, nivel, danio, alcance, fila, columna);
    }
}
