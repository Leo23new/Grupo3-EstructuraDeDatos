package Java;
public class Persona {
    private String nombre;
    private boolean infectado;
    private boolean manoscontaminadas;

    public Persona(String nombre, boolean infectado, boolean manoscontaminadas) {
        this.nombre = nombre;
        this.infectado = infectado;
        this.manoscontaminadas= manoscontaminadas;
    }
    public String getNombre() {
        return nombre;
    }
    public boolean isInfectado() {
        return infectado;
    }
    public boolean isManoscontaminadas() {
        return manoscontaminadas;
    }

    public void lavarmanos() {
        manoscontaminadas = false;
        System.out.println(nombre + " se ha lavado las manos.");
        System.out.println("Las manos de " + nombre + " ya no están contaminadas.");
    }
    public void contaminar() {
        manoscontaminadas = true;
        System.out.println(nombre + " se ha contaminado las manos.");
        System.out.println("Las manos de " + nombre + " están contaminadas.");
    }
    public void tocarRostro(){
        System.out.println(nombre + " se ha tocado el rostro.");
        if(manoscontaminadas){
            infectado=true;
            System.out.println(nombre + " se ha infectado al tocarse el rostro con las manos contaminadas.");
        } else {
            System.out.println(nombre + " se ha tocado el rostro sin las manos contaminadas.");
        }
    }
    public void mostrarEstado(){
        System.out.println("Nombre: " + nombre);
        System.out.println("Infectado: " + infectado);
        System.out.println("Manos contaminadas: " + manoscontaminadas);
    }
}