public class RegistroEstudiantes {

    private static final int MAX_ESTUDIANTES = 20;
    private Estudiante[] estudiantes;
    private int cantidad;

    public RegistroEstudiantes() {
        this.estudiantes = new Estudiante[MAX_ESTUDIANTES];
        this.cantidad = 0;
    }
    public int getCantidad() {
        return cantidad;
    }
    public boolean estaLleno() {
        return cantidad == MAX_ESTUDIANTES;
    }

}