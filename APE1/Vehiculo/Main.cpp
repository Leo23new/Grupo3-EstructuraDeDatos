#include <iostream>
#include <string>
#include <algorithm>
#include <cctype>
#include "Vehiculo.h"
#include "Automovil.h"
#include "Motocicleta.h"
#include "RegistroVehiculos.h"

using namespace std;

static string leerLinea() {
    string linea;
    if (!getline(cin, linea)) {
        cout << "\nEntrada finalizada. Cerrando el programa." << endl;
        exit(0);
    }
    return linea;
}

static int leerEntero(const string& mensaje) {
    while (true) {
        cout << mensaje;
        string linea = leerLinea();
        try {
            size_t pos;
            int valor = stoi(linea, &pos);
            if (pos != linea.size()) throw invalid_argument("sobrante");
            return valor;
        } catch (...) {
            cout << "Ingrese un numero entero valido." << endl;
        }
    }
}

static double leerDouble(const string& mensaje) {
    while (true) {
        cout << mensaje;
        string linea = leerLinea();
        try {
            size_t pos;
            double valor = stod(linea, &pos);
            if (pos != linea.size()) throw invalid_argument("sobrante");
            return valor;
        } catch (...) {
            cout << "Ingrese un numero valido." << endl;
        }
    }
}

// Equivalente a Scanner.nextBoolean(): acepta "true"/"false"
static bool leerBooleano(const string& mensaje) {
    while (true) {
        cout << mensaje;
        string linea = leerLinea();
        string minus = linea;
        transform(minus.begin(), minus.end(), minus.begin(), ::tolower);
        if (minus == "true") return true;
        if (minus == "false") return false;
        cout << "Ingrese 'true' o 'false'." << endl;
    }
}

static string leerTexto(const string& mensaje) {
    cout << mensaje;
    return leerLinea();
}

static void registrarAutomovil(RegistroVehiculos& registro) {
    cout << "\n--- REGISTRAR AUTOMOVIL ---" << endl;

    string placa = leerTexto("Placa: ");
    string marca = leerTexto("Marca: ");
    string modelo = leerTexto("Modelo: ");
    int anio = leerEntero("Año: ");
    double precio = leerDouble("Precio: ");
    bool disponible = leerBooleano("Disponible (true/false): ");
    int numeroPuertas = leerEntero("Numero de puertas: ");
    bool electrico = leerBooleano("Es electrico (true/false): ");

    // CONCEPTO: INSTANCIACION DE OBJETOS
    // CONCEPTO: POLIMORFISMO (una variable de tipo Vehiculo* apunta a un Automovil real)
    Vehiculo* automovil = new Automovil(placa, marca, modelo, anio, precio,
                                         disponible, numeroPuertas, electrico);

    if (registro.registrar(automovil)) {
        cout << "\nAutomovil registrado correctamente." << endl;
    } else {
        cout << "\nNo se pudo registrar el automovil." << endl;
        cout << "La placa puede estar repetida o el registro esta lleno." << endl;
        delete automovil;
    }
}

static void registrarMotocicleta(RegistroVehiculos& registro) {
    cout << "\n--- REGISTRAR MOTOCICLETA ---" << endl;

    string placa = leerTexto("Placa: ");
    string marca = leerTexto("Marca: ");
    string modelo = leerTexto("Modelo: ");
    int anio = leerEntero("Año: ");
    double precio = leerDouble("Precio: ");
    bool disponible = leerBooleano("Disponible (true/false): ");
    int cilindrada = leerEntero("Cilindrada (cc): ");
    bool tieneMaletero = leerBooleano("Tiene maletero (true/false): ");

    // CONCEPTO: INSTANCIACION
    // CONCEPTO: POLIMORFISMO
    Vehiculo* motocicleta = new Motocicleta(placa, marca, modelo, anio, precio,
                                             disponible, cilindrada, tieneMaletero);

    if (registro.registrar(motocicleta)) {
        cout << "\nMotocicleta registrada correctamente." << endl;
    } else {
        cout << "\nNo se pudo registrar la motocicleta." << endl;
        cout << "La placa puede estar repetida o el registro esta lleno." << endl;
        delete motocicleta;
    }
}

int main() {
    // CONCEPTO: INSTANCIACION DE OBJETOS
    RegistroVehiculos registro;

    int opcion;

    do {
        cout << "\n=================================" << endl;
        cout << "       REGISTRO DE VEHICULOS" << endl;
        cout << "=================================" << endl;
        cout << "1. Registrar Automovil" << endl;
        cout << "2. Registrar Motocicleta" << endl;
        cout << "3. Mostrar Vehiculos" << endl;
        cout << "4. Mostrar cantidad registrada" << endl;
        cout << "5. Salir" << endl;

        opcion = leerEntero("Seleccione una opcion: ");

        // CONCEPTO: SWITCH (equivalente al "switch" con "->" de Java 14+)
        switch (opcion) {
            case 1:
                registrarAutomovil(registro);
                break;
            case 2:
                registrarMotocicleta(registro);
                break;
            case 3:
                registro.mostrarTodos();
                break;
            case 4:
                cout << "\nCantidad de vehiculos registrados: "
                     << registro.getCantidad() << endl;
                break;
            case 5:
                cout << "\nPrograma finalizado." << endl;
                break;
            default:
                cout << "\nOpcion no valida." << endl;
        }

    } while (opcion != 5);

    return 0;
}