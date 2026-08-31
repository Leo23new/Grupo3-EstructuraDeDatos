import java.time.LocalDate;
import java.util.Scanner;

public class Main{

    static Scanner teclado = new Scanner(System.in);
    static RegistroEstudiantes registro = new RegistroEstudiantes();
public static void main(String[] args){
        int opcion;

        do {

            mostrarMenu();
            opcion = leerEntero("ingrese una opcion: ");

            switch (opcion) {

                case 1:
                    menuEstudiantes();
                    break;

                case 2:
                    registrarCalificaciones();
                    break;

                case 3:
                    promedioEstudiante();
                    break;

                case 4:
                    promedioCurso();
                    break;

                case 0:
                    System.out.println("Programa finalizado.");
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }

        } while (opcion != 0);
    }

    // ==========================================
    // MENÚ PRINCIPAL
    // ==========================================

    public static void mostrarMenu() {

        System.out.println("=================================");
        System.out.println("       GESTOR DE PERSONAS");
        System.out.println("=================================");
        System.out.println("1.- Estudiantes.");
        System.out.println("2.- Registro de calificaciones.");
        System.out.println("3.- Promedio de notas de un estudiante.");
        System.out.println("4.- Promedio de notas del curso.");
        System.out.println("0.- Salir");
    }

    // ==========================================
    // OPCIÓN 1 - ESTUDIANTES
    // ==========================================

    public static void menuEstudiantes() {

        int opcion;

        do {

            registro.listar();

            System.out.println("========== SUBMENU ESTUDIANTES ==========");
            System.out.println("1.- Ingresar estudiante");
            System.out.println("2.- Modificar estudiante");
            System.out.println("3.- Eliminar estudiante");
            System.out.println("0.- Regresar al menu principal");

            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {

                case 1:
                    ingresarEstudiante();
                    break;

                case 2:
                    modificarEstudiante();
                    break;

                case 3:
                    eliminarEstudiante();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }

        } while (opcion != 0);
    }
    // ==========================================
    // METODOS PARA LEER ENTEROS Y TEXTOS DESDE CONSOLA
    // ==========================================

    public static String leerTexto(String mensaje) {

        System.out.print(mensaje);
        return teclado.nextLine();
    }

    public static int leerEntero(String mensaje) {

        while (true) {

            try {

                System.out.print(mensaje);
                return Integer.parseInt(teclado.nextLine());

            } catch (NumberFormatException e) {

                System.out.println(
                        "Ingrese un numero entero valido."
                );
            }
        }
    }
    // ==========================================
    // INGRESAR ESTUDIANTE
    // ==========================================

    public static void ingresarEstudiante() {

        if (registro.estaLleno()) {

            System.out.println("Ya se registraron los 20 estudiantes.");
            System.out.println("No se pueden ingresar más estudiantes.");
            return;
        }

        System.out.println("========== INGRESAR ESTUDIANTE ==========");

        String cedula = leerTexto("Cedula: ");

        if (registro.buscarPorCedula(cedula) != null) {

            System.out.println("Ya existe un estudiante con esa cedula.");
            return;
        }

        String nombres = leerTexto("Nombres: ");
        String apellidos = leerTexto("Apellidos: ");

        LocalDate fechaNacimiento = leerFecha(
                "Fecha de nacimiento (AAAA-MM-DD): "
        );

        Estudiante estudiante = new Estudiante(
                cedula,
                nombres,
                apellidos,
                fechaNacimiento
        );

        if (registro.registrar(estudiante)) {

            System.out.println("Estudiante registrado correctamente.");

        } else {

            System.out.println("No se pudo registrar el estudiante.");
        }

        repetirAccion("¿Desea ingresar otro estudiante? (s/n): ",
                new Runnable() {
                    public void run() {
                        ingresarEstudiante();
                    }
                });
    }
    public static double leerDouble(String mensaje) {

        while (true) {

            try {

                System.out.print(mensaje);
                return Double.parseDouble(teclado.nextLine());

            } catch (NumberFormatException e) {

                System.out.println(
                        "Ingrese un número válido."
                );
            }
        }
    }

    public static LocalDate leerFecha(String mensaje) {

        while (true) {

            try {

                System.out.print(mensaje);
                return LocalDate.parse(teclado.nextLine());

            } catch (Exception e) {

                System.out.println(
                        "Formato incorrecto. Use AAAA-MM-DD."
                );
            }
        }
    }

  // ==========================================
    // MODIFICAR ESTUDIANTE
    // ==========================================

    public static void modificarEstudiante() {

        if (registro.getCantidad() == 0) {

            System.out.println("No hay estudiantes registrados.");
            return;
        }

        int numero = leerEntero(
                "Ingrese el autonumérico del estudiante: "
        );

        Estudiante estudiante = registro.buscarPorAutonumerico(numero);

        if (estudiante == null) {

            System.out.println("No existe ese autonumérico.");
            return;
        }

        System.out.println("========== DATOS ACTUALES ==========");
        estudiante.mostrarDatos();

        System.out.println("========== NUEVOS DATOS ==========");

        String cedula = leerTexto("Nueva cedula: ");

        String nombres = leerTexto("Nuevos nombres: ");

        String apellidos = leerTexto("Nuevos apellidos: ");

        LocalDate fechaNacimiento = leerFecha(
                "Nueva fecha de nacimiento (AAAA-MM-DD): "
        );

        Estudiante nuevo = new Estudiante(
                cedula,
                nombres,
                apellidos,
                fechaNacimiento
        );

        if (registro.modificar(numero, nuevo)) {

            System.out.println("Estudiante modificado correctamente.");

        } else {

            System.out.println(
                    "No se pudo modificar. "
                    + "La cédula puede estar repetida."
            );
        }

        repetirAccion("¿Desea modificar otro estudiante? (s/n): ",
                new Runnable() {
                    public void run() {
                        modificarEstudiante();
                    }
                });
    }

    // ==========================================
    // ELIMINAR ESTUDIANTE
    // ==========================================

    public static void eliminarEstudiante() {

        if (registro.getCantidad() == 0) {

            System.out.println("No hay estudiantes registrados.");
            System.out.println("No se puede eliminar ningún estudiante.");
            return;
        }

        registro.listar();

        int numero = leerEntero(
                "\nIngrese el autonumérico que desea eliminar: "
        );

        Estudiante estudiante = registro.buscarPorAutonumerico(numero);

        if (estudiante == null) {

            System.out.println("\nNo existe ese autonumérico.");
            return;
        }

        System.out.println("Estudiante seleccionado:");

        estudiante.mostrarDatos();

        String respuesta = leerTexto(
                "¿Está seguro de eliminarlo? (s/n): "
        );

        if (respuesta.equalsIgnoreCase("s")) {

            if (registro.eliminar(numero)) {

                System.out.println(
                        "Estudiante eliminado correctamente."
                );
            }

        } else {

            System.out.println("Operación cancelada.");
        }

        repetirAccion("¿Desea eliminar otro estudiante? (s/n): ",
                new Runnable() {
                    public void run() {
                        eliminarEstudiante();
                    }
                });
    }
    // ==========================================
    // OPCION 2 - CALIFICACIONES
    // ==========================================

    public static void registrarCalificaciones() {

        if (registro.getCantidad() == 0) {

            System.out.println("No hay estudiantes registrados.");
            return;
        }

        System.out.println("========== REGISTRO DE CALIFICACIONES ==========");

        while (true) {

            String cedula = leerTexto(
                    "Ingrese la cedula del estudiante (0 para regresar): "
            );

            if (cedula.equals("0")) {
                return;
            }

            Estudiante estudiante = registro.buscarPorCedula(cedula);

            if (estudiante == null) {

                System.out.println(
                        "No se encontro un estudiante con esa cedula."
                );

                String respuesta = leerTexto(
                        "¿Desea ingresar otra cedula? (s/n): "
                );

                if (!respuesta.equalsIgnoreCase("s")) {
                    return;
                }

                continue;
            }

            System.out.println("========== ESTUDIANTE ==========");
            System.out.println("Nombres: " + estudiante.getNombres());
            System.out.println("Apellidos: " + estudiante.getApellidos());
            System.out.println("Edad: " + estudiante.getEdad());

            menuCalificaciones(estudiante);

            return;
        }
    }

   // ==========================================
    // MENU DE CALIFICACIONES
    // ==========================================

    public static void menuCalificaciones(Estudiante estudiante) {

        int opcion;

        do {

            mostrarNotas(estudiante);

            System.out.println("========== CALIFICACIONES ==========");
            System.out.println("1.- Insertar nota");
            System.out.println("2.- Modificar nota");
            System.out.println("3.- Eliminar nota");
            System.out.println("0.- Regresar");

            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) {

                case 1:
                    insertarNota(estudiante);
                    break;

                case 2:
                    modificarNota(estudiante);
                    break;

                case 3:
                    eliminarNota(estudiante);
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }

        } while (opcion != 0);
    }

    // ==========================================
    // MOSTRAR NOTAS
    // ==========================================

    public static void mostrarNotas(Estudiante estudiante) {

        System.out.println("========== NOTAS REGISTRADAS ==========");

        boolean hayNotas = false;

        for (int i = 0; i < estudiante.getNotas().length; i++) {

            if (estudiante.getNotas()[i] != 0) {

                System.out.println(
                        (i + 1) + ". " + estudiante.getNotas()[i]
                );

                hayNotas = true;
            }
        }

        if (!hayNotas) {
            System.out.println("No hay calificaciones registradas.");
        }
    }

    // ==========================================
    // INSERTAR NOTA
    // ==========================================

    public static void insertarNota(Estudiante estudiante) {

        if (estudiante.cantidadNotas() == 7) {

            System.out.println(
                    "Se han ingresado todas las calificaciones posibles."
            );

            return;
        }

        double nota;

        do {

            nota = leerDouble("Ingrese la nota (0-10): ");

            if (nota < 0 || nota > 10) {

                System.out.println(
                        "La nota debe estar entre 0 y 10."
                );
            }

        } while (nota < 0 || nota > 10);

        if (estudiante.agregarNota(nota)) {

            System.out.println("Nota registrada correctamente.");
        }
    }

    // ==========================================
    // MODIFICAR NOTA
    // ==========================================

    public static void modificarNota(Estudiante estudiante) {

        if (estudiante.cantidadNotas() == 0) {

            System.out.println("No existen notas para modificar.");
            return;
        }

        mostrarNotas(estudiante);

        int posicion = leerEntero(
                "Ingrese el número de la nota que desea modificar: "
        );

        double nuevaNota;

        do {

            nuevaNota = leerDouble(
                    "Ingrese la nueva nota (0-10): "
            );

            if (nuevaNota < 0 || nuevaNota > 10) {

                System.out.println(
                        "La nota debe estar entre 0 y 10."
                );
            }

        } while (nuevaNota < 0 || nuevaNota > 10);

        if (estudiante.modificarNota(posicion - 1, nuevaNota)) {

            System.out.println("Nota modificada correctamente.");

        } else {

            System.out.println("No se pudo modificar la nota.");
        }
    }

    // ==========================================
    // ELIMINAR NOTA
    // ==========================================

    public static void eliminarNota(Estudiante estudiante) {

        if (estudiante.cantidadNotas() == 0) {

            System.out.println("No existen notas para eliminar.");
            return;
        }

        mostrarNotas(estudiante);

        int posicion = leerEntero(
                "Ingrese el numero de la nota que desea eliminar: "
        );

        if (estudiante.eliminarNota(posicion - 1)) {

            System.out.println("Nota eliminada correctamente.");

        } else {

            System.out.println("No se pudo eliminar la nota.");
        }
    }
    // ==========================================
    // OPCION 3 - PROMEDIO ESTUDIANTE
    // ==========================================

    public static void promedioEstudiante() {

        if (registro.getCantidad() == 0) {

            System.out.println("No hay estudiantes registrados.");
            return;
        }

        String cedula = leerTexto(
                "Ingrese la cEdula del estudiante: "
        );

        Estudiante estudiante = registro.buscarPorCedula(cedula);

        if (estudiante == null) {

            System.out.println(
                    "No se encontrO un estudiante con la cedula indicada."
            );

            return;
        }

        System.out.println("========== INFORMACION ==========");
        System.out.println("Nombres: " + estudiante.getNombres());
        System.out.println("Apellidos: " + estudiante.getApellidos());
        System.out.println("Edad: " + estudiante.getEdad());

        if (estudiante.cantidadNotas() == 0) {

            System.out.println("Promedio: No tiene calificaciones.");

        } else {

            System.out.printf(
                    "Promedio: %.2f%n",
                    estudiante.calcularPromedio()
            );
        }
    }
    // ==========================================
    // OPCION 4 - PROMEDIO DEL CURSO
    // ==========================================

    public static void promedioCurso() {

        double promedio = registro.calcularPromedioCurso();

        if (promedio == 0 && !existenNotas()) {

            System.out.println(
                    "No se han registrado calificaciones de estudiantes."
            );

        } else {

            System.out.printf(
                    "Promedio general del curso: %.2f%n",
                    promedio
            );
        }
    }

    // Verificar si existen notas
    public static boolean existenNotas() {

        for (int i = 1; i <= registro.getCantidad(); i++) {

            Estudiante estudiante =
                    registro.buscarPorAutonumerico(i);

            if (estudiante.cantidadNotas() > 0) {
                return true;
            }
        }

        return false;
    }


}