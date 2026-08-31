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
    // Registrar estudiante
    public boolean registrar(Estudiante estudiante) {

        // Verificar que no esté lleno
        if (estaLleno()) {
            return false;
        }

        // Verificar cedula repetida
        if (buscarPorCedula(estudiante.getCedula()) != null) {
            return false;
        }

        estudiantes[cantidad] = estudiante;
        cantidad++;

        return true;
    }

    // Buscar por cedula
    public Estudiante buscarPorCedula(String cedula) {

        for (int i = 0; i < cantidad; i++) {

            if (estudiantes[i].getCedula().equals(cedula)) {
                return estudiantes[i];
            }
        }

        return null;
    }

}