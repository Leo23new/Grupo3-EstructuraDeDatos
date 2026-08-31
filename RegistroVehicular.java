import java.util.Scanner;

// ============================================================
// CONCEPTO: CLASE ABSTRACTA
// Clase padre que contiene los datos comunes de los vehículos.
// ============================================================
abstract class Vehiculo {

    // CONCEPTO: ENCAPSULAMIENTO
    protected String placa;
    protected String marca;
    protected String modelo;
    protected int anio;
    protected double precio;
    protected boolean disponible;

    // Constructor de la clase Vehiculo
    public Vehiculo(String placa, String marca, String modelo,
                    int anio, double precio, boolean disponible) {

        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.precio = precio;
        this.disponible = disponible;
    }

    // Obtener la placa
    public String getPlaca() {
        return placa;
    }

    // Mostrar los datos comunes
    public void mostrarDatosComunes() {
        System.out.println("Placa: " + placa);
        System.out.println("Marca: " + marca);
        System.out.println("Modelo: " + modelo);
        System.out.println("Año: " + anio);
        System.out.println("Precio: $" + precio);
        System.out.println("Disponible: "
                + (disponible ? "Si" : "No"));
    }

    // CONCEPTO: POLIMORFISMO
    public abstract void mostrarInformacion();
}


// ============================================================
// CONCEPTO: HERENCIA
// Automovil hereda de Vehiculo.
// ============================================================
class Automovil extends Vehiculo {

    // CONCEPTO: ENCAPSULAMIENTO
    private final int numeroPuertas;
    private final boolean electrico;

    // Constructor
    public Automovil(String placa, String marca, String modelo,
                     int anio, double precio, boolean disponible,
                     int numeroPuertas, boolean electrico) {

        // CONCEPTO: HERENCIA
        super(placa, marca, modelo, anio, precio, disponible);

        this.numeroPuertas = numeroPuertas;
        this.electrico = electrico;
    }

    // CONCEPTO: POLIMORFISMO
    @Override
    public void mostrarInformacion() {

        System.out.println("\n----- AUTOMOVIL -----");

        mostrarDatosComunes();

        System.out.println("Numero de puertas: " + numeroPuertas);
        System.out.println("Electrico: "
                + (electrico ? "Si" : "No"));
    }
}


// ============================================================
// CONCEPTO: HERENCIA
// Motocicleta hereda de Vehiculo.
// ============================================================
class Motocicleta extends Vehiculo {

    private final int cilindrada;
    private final boolean tieneMaletero;

    // Constructor
    public Motocicleta(String placa, String marca, String modelo,
                       int anio, double precio, boolean disponible,
                       int cilindrada, boolean tieneMaletero) {

        super(placa, marca, modelo, anio, precio, disponible);

        this.cilindrada = cilindrada;
        this.tieneMaletero = tieneMaletero;
    }

    // CONCEPTO: POLIMORFISMO
    @Override
    public void mostrarInformacion() {

        System.out.println("\n----- MOTOCICLETA -----");

        mostrarDatosComunes();

        System.out.println("Cilindrada: " + cilindrada + " cc");
        System.out.println("Tiene maletero: "
                + (tieneMaletero ? "Si" : "No"));
    }
}


// ============================================================
// CONCEPTO: TDA REGISTRO DE VEHICULOS
// Administra el arreglo de vehículos.
// ============================================================
class RegistroVehiculos {

    // CONCEPTO: ARREGLO DE TAMAÑO FIJO
    private static final int CAPACIDAD = 10;

    private final Vehiculo[] vehiculos = new Vehiculo[CAPACIDAD];

    // CONCEPTO: DATO PRIMITIVO
    private int cantidad = 0;

    // Registrar un vehículo
    public boolean registrar(Vehiculo nuevoVehiculo) {

        if (nuevoVehiculo == null) {
            return false;
        }

        if (estaLleno()) {
            return false;
        }

        if (existePlaca(nuevoVehiculo.getPlaca())) {
            return false;
        }

        vehiculos[cantidad] = nuevoVehiculo;
        cantidad++;

        return true;
    }

    // CONCEPTO: POLIMORFISMO
    public void mostrarTodos() {

        if (cantidad == 0) {
            System.out.println("\nNo hay vehiculos registrados.");
            return;
        }

        System.out.println("\n================================");
        System.out.println("       VEHICULOS REGISTRADOS");
        System.out.println("================================");

        for (int i = 0; i < cantidad; i++) {

            // Java ejecuta el método correspondiente
            // al objeto real: Automovil o Motocicleta.
            vehiculos[i].mostrarInformacion();

            System.out.println("-------------------------------");
        }
    }

    // Comprobar si existe una placa
    public boolean existePlaca(String placa) {

        for (int i = 0; i < cantidad; i++) {

            if (vehiculos[i].getPlaca().equalsIgnoreCase(placa)) {
                return true;
            }
        }

        return false;
    }

    // Comprobar si el arreglo está lleno
    public boolean estaLleno() {
        return cantidad == CAPACIDAD;
    }

    // Obtener cantidad de vehículos
    public int getCantidad() {
        return cantidad;
    }
}


// ============================================================
// CLASE PRINCIPAL
// El archivo debe llamarse RegistroVehicular.java
// ============================================================
public class RegistroVehicular {

    public static void main(String[] args) {

        // CONCEPTO: INSTANCIACION DE OBJETOS
        RegistroVehiculos registro = new RegistroVehiculos();

        // CONCEPTO: TRY-WITH-RESOURCES
        // Scanner se cierra automáticamente al terminar.
        try (Scanner scanner = new Scanner(System.in)) {

            int opcion;


            // MENU PRINCIPAL

            do {

                System.out.println("\n=================================");
                System.out.println("       REGISTRO DE VEHICULOS");
                System.out.println("=================================");
                System.out.println("1. Registrar Automovil");
                System.out.println("2. Registrar Motocicleta");
                System.out.println("3. Mostrar Vehiculos");
                System.out.println("4. Mostrar cantidad registrada");
                System.out.println("5. Salir");
                System.out.print("Seleccione una opcion: ");

                opcion = scanner.nextInt();
                scanner.nextLine();

                // CONCEPTO: SWITCH
                switch (opcion) {

                    case 1 -> registrarAutomovil(scanner, registro);

                    case 2 -> registrarMotocicleta(scanner, registro);

                    case 3 -> registro.mostrarTodos();

                    case 4 -> System.out.println(
                            "\nCantidad de vehiculos registrados: "
                            + registro.getCantidad()
                    );

                    case 5 -> System.out.println(
                            "\nPrograma finalizado."
                    );

                    default -> System.out.println(
                            "\nOpcion no valida."
                    );
                }

            } while (opcion != 5);
        }
    }


    // ============================================================
    // REGISTRAR AUTOMOVIL
    // ============================================================
    private static void registrarAutomovil(
            Scanner scanner,
            RegistroVehiculos registro) {

        System.out.println("\n--- REGISTRAR AUTOMOVIL ---");

        System.out.print("Placa: ");
        String placa = scanner.nextLine();

        System.out.print("Marca: ");
        String marca = scanner.nextLine();

        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();

        System.out.print("Año: ");
        int anio = scanner.nextInt();

        System.out.print("Precio: ");
        double precio = scanner.nextDouble();

        System.out.print("Disponible (true/false): ");
        boolean disponible = scanner.nextBoolean();

        System.out.print("Numero de puertas: ");
        int numeroPuertas = scanner.nextInt();

        System.out.print("Es electrico (true/false): ");
        boolean electrico = scanner.nextBoolean();

        scanner.nextLine();

        // CONCEPTO: INSTANCIACION
        // CONCEPTO: POLIMORFISMO
        Vehiculo automovil = new Automovil(
                placa,
                marca,
                modelo,
                anio,
                precio,
                disponible,
                numeroPuertas,
                electrico
        );

        if (registro.registrar(automovil)) {

            System.out.println(
                    "\nAutomovil registrado correctamente."
            );

        } else {

            System.out.println(
                    "\nNo se pudo registrar el automovil."
            );

            System.out.println(
                    "La placa puede estar repetida "
                    + "o el registro esta lleno."
            );
        }
    }



    // REGISTRAR MOTOCICLETA

    private static void registrarMotocicleta(
            Scanner scanner,
            RegistroVehiculos registro) {

        System.out.println("\n--- REGISTRAR MOTOCICLETA ---");

        System.out.print("Placa: ");
        String placa = scanner.nextLine();

        System.out.print("Marca: ");
        String marca = scanner.nextLine();

        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();

        System.out.print("Año: ");
        int anio = scanner.nextInt();

        System.out.print("Precio: ");
        double precio = scanner.nextDouble();

        System.out.print("Disponible (true/false): ");
        boolean disponible = scanner.nextBoolean();

        System.out.print("Cilindrada (cc): ");
        int cilindrada = scanner.nextInt();

        System.out.print("Tiene maletero (true/false): ");
        boolean tieneMaletero = scanner.nextBoolean();

        scanner.nextLine();

        // CONCEPTO: INSTANCIACION
        // CONCEPTO: POLIMORFISMO
        Vehiculo motocicleta = new Motocicleta(
                placa,
                marca,
                modelo,
                anio,
                precio,
                disponible,
                cilindrada,
                tieneMaletero
        );

        if (registro.registrar(motocicleta)) {

            System.out.println(
                    "\nMotocicleta registrada correctamente."
            );

        } else {

            System.out.println(
                    "\nNo se pudo registrar la motocicleta."
            );

            System.out.println(
                    "La placa puede estar repetida "
                    + "o el registro esta lleno."
            );
        }
    }
}