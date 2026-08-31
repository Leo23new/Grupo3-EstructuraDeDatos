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

}