/**
 * Lista secuencial de torres
 * Representa una torre defensiva colocada por el jugador.
 */
public class Torre {
    private int id;
    private String nombre;
    private String tipo;
    private int posicion;
    private int danio;
    private int rango;
    private int costo;
    private boolean activa;

    public Torre(int id, String nombre, String tipo, int posicion, int danio, int rango, int costo) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.posicion = posicion;
        this.danio = danio;
        this.rango = rango;
        this.costo = costo;
        this.activa = true;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
    public int getPosicion() { return posicion; }
    public int getDanio() { return danio; }
    public int getRango() { return rango; }
    public int getCosto() { return costo; }
    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }

    /** Verifica si una posicion de enemigo esta dentro del rango de ataque. */
    public boolean estaEnRango(int posicionEnemigo) {
        return Math.abs(this.posicion - posicionEnemigo) <= this.rango;
    }

    @Override
    public String toString() {
        return "Torre{id=" + id + ", nombre='" + nombre + "', tipo='" + tipo +
                "', posicion=" + posicion + ", danio=" + danio +
                ", rango=" + rango + ", costo=" + costo + "}";
    }
}