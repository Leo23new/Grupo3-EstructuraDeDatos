package Java;

public class Objeto {
    private String nombre;
    private boolean infectado;

    public Objeto(String nombre, boolean infectado) {
        this.nombre = nombre;
        this.infectado = infectado;
    }
    public String getNombre() {
        return nombre;
    }
    public boolean isInfectado() {
        return infectado;
    }

    public void contaminar() {
        infectado = true;
        System.out.println(nombre + " se ha contaminado.");
        System.out.println(nombre + " está contaminado.");
    }
    public void limpio () {
        infectado = false;
        System.out.println(nombre + " se ha limpiado.");
        System.out.println(nombre + " ya no está contaminado.");
    }
    public void mostrarEstado(){
        System.out.println("Nombre: " + nombre);
        System.out.println("Infectado: " + infectado);
    }
}