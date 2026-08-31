#include <iostream>
#include <string>
#include <sstream>
#include <functional>
#include <iomanip>
#include "Fecha.h"
#include "Estudiante.h"
#include "RegistroEstudiantes.h"

using namespace std;

// Estado global (equivalente a los "static" de Main.java)
static RegistroEstudiantes registro;

// ---- Prototipos (equivalentes a que Java pueda referenciar métodos
// definidos más abajo en la misma clase) ----
void mostrarMenu();
void menuEstudiantes();
string leerTexto(const string& mensaje);
int leerEntero(const string& mensaje);
double leerDouble(const string& mensaje);
Fecha leerFecha(const string& mensaje);
void ingresarEstudiante();
void modificarEstudiante();
void eliminarEstudiante();
void registrarCalificaciones();
void menuCalificaciones(Estudiante* estudiante);
void mostrarNotas(Estudiante* estudiante);
void insertarNota(Estudiante* estudiante);
void modificarNota(Estudiante* estudiante);
void eliminarNota(Estudiante* estudiante);
void promedioEstudiante();
void promedioCurso();
bool existenNotas();
void repetirAccion(const string& mensaje, const function<void()>& accion);

int main() {
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
                cout << "Programa finalizado." << endl;
                break;
            default:
                cout << "Opcion no valida." << endl;
        }

    } while (opcion != 0);

    return 0;
}

// ==========================================
// MENÚ PRINCIPAL
// ==========================================
void mostrarMenu() {
    cout << "=================================" << endl;
    cout << "       GESTOR DE PERSONAS" << endl;
    cout << "=================================" << endl;
    cout << "1.- Estudiantes." << endl;
    cout << "2.- Registro de calificaciones." << endl;
    cout << "3.- Promedio de notas de un estudiante." << endl;
    cout << "4.- Promedio de notas del curso." << endl;
    cout << "0.- Salir" << endl;
}

// ==========================================
// OPCIÓN 1 - ESTUDIANTES
// ==========================================
void menuEstudiantes() {
    int opcion;

    do {
        registro.listar();

        cout << "========== SUBMENU ESTUDIANTES ==========" << endl;
        cout << "1.- Ingresar estudiante" << endl;
        cout << "2.- Modificar estudiante" << endl;
        cout << "3.- Eliminar estudiante" << endl;
        cout << "0.- Regresar al menu principal" << endl;

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
                cout << "Opcion no valida." << endl;
        }

    } while (opcion != 0);
}

// ==========================================
// MÉTODOS PARA LEER ENTEROS Y TEXTOS DESDE CONSOLA
// ==========================================
// Lee una línea de forma segura; si la entrada se agotó (EOF), termina
// el programa con gracia en lugar de quedar en bucle infinito.
static string leerLinea() {
    string linea;
    if (!getline(cin, linea)) {
        cout << "\nEntrada finalizada. Cerrando el programa." << endl;
        exit(0);
    }
    return linea;
}

string leerTexto(const string& mensaje) {
    cout << mensaje;
    return leerLinea();
}

int leerEntero(const string& mensaje) {
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

double leerDouble(const string& mensaje) {
    while (true) {
        cout << mensaje;
        string linea = leerLinea();
        try {
            size_t pos;
            double valor = stod(linea, &pos);
            if (pos != linea.size()) throw invalid_argument("sobrante");
            return valor;
        } catch (...) {
            cout << "Ingrese un número válido." << endl;
        }
    }
}

Fecha leerFecha(const string& mensaje) {
    Fecha resultado;
    while (true) {
        cout << mensaje;
        string linea = leerLinea();
        if (parsearFecha(linea, resultado)) {
            return resultado;
        }
        cout << "Formato incorrecto. Use AAAA-MM-DD." << endl;
    }
}

// ==========================================
// INGRESAR ESTUDIANTE
// ==========================================
void ingresarEstudiante() {
    if (registro.estaLleno()) {
        cout << "Ya se registraron los 20 estudiantes." << endl;
        cout << "No se pueden ingresar más estudiantes." << endl;
        return;
    }

    cout << "========== INGRESAR ESTUDIANTE ==========" << endl;

    string cedula = leerTexto("Cedula: ");

    if (registro.buscarPorCedula(cedula) != nullptr) {
        cout << "Ya existe un estudiante con esa cedula." << endl;
        return;
    }

    string nombres = leerTexto("Nombres: ");
    string apellidos = leerTexto("Apellidos: ");
    Fecha fechaNacimiento = leerFecha("Fecha de nacimiento (AAAA-MM-DD): ");

    Estudiante* estudiante = new Estudiante(cedula, nombres, apellidos, fechaNacimiento);

    if (registro.registrar(estudiante)) {
        cout << "Estudiante registrado correctamente." << endl;
    } else {
        cout << "No se pudo registrar el estudiante." << endl;
        delete estudiante; // evitar fuga de memoria si el registro lo rechazó
    }

    repetirAccion("¿Desea ingresar otro estudiante? (s/n): ", []() {
        ingresarEstudiante();
    });
}

// ==========================================
// MODIFICAR ESTUDIANTE
// ==========================================
void modificarEstudiante() {
    if (registro.getCantidad() == 0) {
        cout << "No hay estudiantes registrados." << endl;
        return;
    }

    int numero = leerEntero("Ingrese el autonumérico del estudiante: ");
    Estudiante* estudiante = registro.buscarPorAutonumerico(numero);

    if (estudiante == nullptr) {
        cout << "No existe ese autonumérico." << endl;
        return;
    }

    cout << "========== DATOS ACTUALES ==========" << endl;
    estudiante->mostrarDatos();

    cout << "========== NUEVOS DATOS ==========" << endl;

    string cedula = leerTexto("Nueva cedula: ");
    string nombres = leerTexto("Nuevos nombres: ");
    string apellidos = leerTexto("Nuevos apellidos: ");
    Fecha fechaNacimiento = leerFecha("Nueva fecha de nacimiento (AAAA-MM-DD): ");

    Estudiante* nuevo = new Estudiante(cedula, nombres, apellidos, fechaNacimiento);

    if (registro.modificar(numero, nuevo)) {
        cout << "Estudiante modificado correctamente." << endl;
    } else {
        cout << "No se pudo modificar. La cédula puede estar repetida." << endl;
        delete nuevo;
    }

    repetirAccion("¿Desea modificar otro estudiante? (s/n): ", []() {
        modificarEstudiante();
    });
}

// ==========================================
// ELIMINAR ESTUDIANTE
// ==========================================
void eliminarEstudiante() {
    if (registro.getCantidad() == 0) {
        cout << "No hay estudiantes registrados." << endl;
        cout << "No se puede eliminar ningún estudiante." << endl;
        return;
    }

    registro.listar();

    int numero = leerEntero("\nIngrese el autonumérico que desea eliminar: ");
    Estudiante* estudiante = registro.buscarPorAutonumerico(numero);

    if (estudiante == nullptr) {
        cout << "\nNo existe ese autonumérico." << endl;
        return;
    }

    cout << "Estudiante seleccionado:" << endl;
    estudiante->mostrarDatos();

    string respuesta = leerTexto("¿Está seguro de eliminarlo? (s/n): ");

    if (respuesta == "s" || respuesta == "S") {
        if (registro.eliminar(numero)) {
            cout << "Estudiante eliminado correctamente." << endl;
        }
    } else {
        cout << "Operación cancelada." << endl;
    }

    repetirAccion("¿Desea eliminar otro estudiante? (s/n): ", []() {
        eliminarEstudiante();
    });
}

// ==========================================
// OPCIÓN 2 - CALIFICACIONES
// ==========================================
void registrarCalificaciones() {
    if (registro.getCantidad() == 0) {
        cout << "No hay estudiantes registrados." << endl;
        return;
    }

    cout << "========== REGISTRO DE CALIFICACIONES ==========" << endl;

    while (true) {
        string cedula = leerTexto("Ingrese la cedula del estudiante (0 para regresar): ");

        if (cedula == "0") {
            return;
        }

        Estudiante* estudiante = registro.buscarPorCedula(cedula);

        if (estudiante == nullptr) {
            cout << "No se encontro un estudiante con esa cedula." << endl;
            string respuesta = leerTexto("¿Desea ingresar otra cedula? (s/n): ");
            if (!(respuesta == "s" || respuesta == "S")) {
                return;
            }
            continue;
        }

        cout << "========== ESTUDIANTE ==========" << endl;
        cout << "Nombres: " << estudiante->getNombres() << endl;
        cout << "Apellidos: " << estudiante->getApellidos() << endl;
        cout << "Edad: " << estudiante->getEdad() << endl;

        menuCalificaciones(estudiante);
        return;
    }
}

// ==========================================
// MENÚ DE CALIFICACIONES
// ==========================================
void menuCalificaciones(Estudiante* estudiante) {
    int opcion;

    do {
        mostrarNotas(estudiante);

        cout << "========== CALIFICACIONES ==========" << endl;
        cout << "1.- Insertar nota" << endl;
        cout << "2.- Modificar nota" << endl;
        cout << "3.- Eliminar nota" << endl;
        cout << "0.- Regresar" << endl;

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
                cout << "Opcion no valida." << endl;
        }

    } while (opcion != 0);
}

// ==========================================
// MOSTRAR NOTAS
// ==========================================
void mostrarNotas(Estudiante* estudiante) {
    cout << "========== NOTAS REGISTRADAS ==========" << endl;

    bool hayNotas = false;
    double* notas = estudiante->getNotas();

    for (int i = 0; i < 7; i++) {
        if (notas[i] != 0) {
            cout << (i + 1) << ". " << notas[i] << endl;
            hayNotas = true;
        }
    }

    if (!hayNotas) {
        cout << "No hay calificaciones registradas." << endl;
    }
}

// ==========================================
// INSERTAR NOTA
// ==========================================
void insertarNota(Estudiante* estudiante) {
    if (estudiante->cantidadNotas() == 7) {
        cout << "Se han ingresado todas las calificaciones posibles." << endl;
        return;
    }

    double nota;
    do {
        nota = leerDouble("Ingrese la nota (0-10): ");
        if (nota < 0 || nota > 10) {
            cout << "La nota debe estar entre 0 y 10." << endl;
        }
    } while (nota < 0 || nota > 10);

    if (estudiante->agregarNota(nota)) {
        cout << "Nota registrada correctamente." << endl;
    }
}

// ==========================================
// MODIFICAR NOTA
// ==========================================
void modificarNota(Estudiante* estudiante) {
    if (estudiante->cantidadNotas() == 0) {
        cout << "No existen notas para modificar." << endl;
        return;
    }

    mostrarNotas(estudiante);

    int posicion = leerEntero("Ingrese el número de la nota que desea modificar: ");

    double nuevaNota;
    do {
        nuevaNota = leerDouble("Ingrese la nueva nota (0-10): ");
        if (nuevaNota < 0 || nuevaNota > 10) {
            cout << "La nota debe estar entre 0 y 10." << endl;
        }
    } while (nuevaNota < 0 || nuevaNota > 10);

    if (estudiante->modificarNota(posicion - 1, nuevaNota)) {
        cout << "Nota modificada correctamente." << endl;
    } else {
        cout << "No se pudo modificar la nota." << endl;
    }
}

// ==========================================
// ELIMINAR NOTA
// ==========================================
void eliminarNota(Estudiante* estudiante) {
    if (estudiante->cantidadNotas() == 0) {
        cout << "No existen notas para eliminar." << endl;
        return;
    }

    mostrarNotas(estudiante);

    int posicion = leerEntero("Ingrese el numero de la nota que desea eliminar: ");

    if (estudiante->eliminarNota(posicion - 1)) {
        cout << "Nota eliminada correctamente." << endl;
    } else {
        cout << "No se pudo eliminar la nota." << endl;
    }
}

// ==========================================
// OPCIÓN 3 - PROMEDIO ESTUDIANTE
// ==========================================
void promedioEstudiante() {
    if (registro.getCantidad() == 0) {
        cout << "No hay estudiantes registrados." << endl;
        return;
    }

    string cedula = leerTexto("Ingrese la cedula del estudiante: ");
    Estudiante* estudiante = registro.buscarPorCedula(cedula);

    if (estudiante == nullptr) {
        cout << "No se encontro un estudiante con la cedula indicada." << endl;
        return;
    }

    cout << "========== INFORMACION ==========" << endl;
    cout << "Nombres: " << estudiante->getNombres() << endl;
    cout << "Apellidos: " << estudiante->getApellidos() << endl;
    cout << "Edad: " << estudiante->getEdad() << endl;

    if (estudiante->cantidadNotas() == 0) {
        cout << "Promedio: No tiene calificaciones." << endl;
    } else {
        cout << fixed << setprecision(2);
        cout << "Promedio: " << estudiante->calcularPromedio() << endl;
    }
}

// ==========================================
// OPCIÓN 4 - PROMEDIO DEL CURSO
// ==========================================
void promedioCurso() {
    double promedio = registro.calcularPromedioCurso();

    if (promedio == 0 && !existenNotas()) {
        cout << "No se han registrado calificaciones de estudiantes." << endl;
    } else {
        cout << fixed << setprecision(2);
        cout << "Promedio general del curso: " << promedio << endl;
    }
}

// Verificar si existen notas
bool existenNotas() {
    for (int i = 1; i <= registro.getCantidad(); i++) {
        Estudiante* estudiante = registro.buscarPorAutonumerico(i);
        if (estudiante->cantidadNotas() > 0) {
            return true;
        }
    }
    return false;
}

// ==========================================
// REPETIR ACCIÓN
// ==========================================
void repetirAccion(const string& mensaje, const function<void()>& accion) {
    string respuesta = leerTexto(mensaje);
    if (respuesta == "s" || respuesta == "S") {
        accion();
    }
}