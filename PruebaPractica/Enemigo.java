
public class Enemigo {
    private int id;
    private String tipo;
    private int vida;
    private int velocidad;
    private int posicion;
    private int recompensa;

    public Enemigo(int id, String tipo, int vida, int velocidad, int posicion, int recompensa) {
        this.id = id;
        this.tipo = tipo;
        this.vida = vida;
        this.velocidad = velocidad;
        this.posicion = posicion;
        this.recompensa = recompensa;
    }

    public int getId() { return id; }
    public String getTipo() { return tipo; }
    public int getVida() { return vida; }
    public void setVida(int vida) { this.vida = vida; }
    public int getVelocidad() { return velocidad; }
    public int getPosicion() { return posicion; }
    public void setPosicion(int posicion) { this.posicion = posicion; }
    public int getRecompensa() { return recompensa; }

    /** Aplica danio recibido, sin bajar de 0. */
    public void recibirDanio(int danio) {
        this.vida -= danio;
        if (this.vida < 0) this.vida = 0;
    }

    public boolean estaMuerto() {
        return this.vida <= 0;
    }

    @Override
    public String toString() {
        return "Enemigo{id=" + id + ", tipo='" + tipo + "', vida=" + vida +
                ", velocidad=" + velocidad + ", posicion=" + posicion +
                ", recompensa=" + recompensa + "}";
    }
}