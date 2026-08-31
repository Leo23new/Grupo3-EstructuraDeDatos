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
    // Buscar por autonumerico 
    //sirve para identificar temporalmente la posicion de cada estudiante dentro del vector y facilitar operaciones como buscar, modificar y eliminar
    public Estudiante buscarPorAutonumerico(int numero) {

        if (numero < 1 || numero > cantidad) {
            return null;
        }

        return estudiantes[numero - 1];
    }

    // Modificar estudiante
    public boolean modificar(int numero, Estudiante nuevoEstudiante) {

        if (numero < 1 || numero > cantidad) {
            return false;
        }

        // Verificar que la nueva cedula no pertenezca a otro estudiante
        for (int i = 0; i < cantidad; i++) {

            if (i != numero - 1 &&
                estudiantes[i].getCedula().equals(nuevoEstudiante.getCedula())) {

                return false;
            }
        }

        // Guarda las notas anteriores
        double[] notasAnteriores = estudiantes[numero - 1].getNotas();

        estudiantes[numero - 1] = nuevoEstudiante;

        // Mantener las notas que ya tenia
        for (int i = 0; i < notasAnteriores.length; i++) {
            estudiantes[numero - 1].getNotas()[i] = notasAnteriores[i];
        }

        return true;
    }

}