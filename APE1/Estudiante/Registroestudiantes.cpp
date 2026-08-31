#include "RegistroEstudiantes.h"
#include <iostream>

RegistroEstudiantes::RegistroEstudiantes() : cantidad(0) {
    for (int i = 0; i < MAX_ESTUDIANTES; i++) {
        estudiantes[i] = nullptr;
    }
}

RegistroEstudiantes::~RegistroEstudiantes() {
    for (int i = 0; i < cantidad; i++) {
        delete estudiantes[i];
    }
}

int RegistroEstudiantes::getCantidad() const {
    return cantidad;
}

bool RegistroEstudiantes::estaLleno() const {
    return cantidad == MAX_ESTUDIANTES;
}

bool RegistroEstudiantes::registrar(Estudiante* estudiante) {
    // Verificar que no esté lleno
    if (estaLleno()) {
        return false;
    }
    // Verificar cedula repetida
    if (buscarPorCedula(estudiante->getCedula()) != nullptr) {
        return false;
    }
    estudiantes[cantidad] = estudiante;
    cantidad++;
    return true;
}

Estudiante* RegistroEstudiantes::buscarPorCedula(const std::string& cedula) {
    for (int i = 0; i < cantidad; i++) {
        if (estudiantes[i]->getCedula() == cedula) {
            return estudiantes[i];
        }
    }
    return nullptr;
}

Estudiante* RegistroEstudiantes::buscarPorAutonumerico(int numero) {
    if (numero < 1 || numero > cantidad) {
        return nullptr;
    }
    return estudiantes[numero - 1];
}

bool RegistroEstudiantes::modificar(int numero, Estudiante* nuevoEstudiante) {
    if (numero < 1 || numero > cantidad) {
        return false;
    }

    // Verificar que la nueva cedula no pertenezca a otro estudiante
    for (int i = 0; i < cantidad; i++) {
        if (i != numero - 1 &&
            estudiantes[i]->getCedula() == nuevoEstudiante->getCedula()) {
            return false;
        }
    }

    // Guardar las notas anteriores (copia local, porque el objeto
    // original se va a liberar antes de restaurar las notas)
    double notasAnteriores[7];
    double* notasOriginales = estudiantes[numero - 1]->getNotas();
    for (int i = 0; i < 7; i++) {
        notasAnteriores[i] = notasOriginales[i];
    }

    delete estudiantes[numero - 1]; // liberar el objeto anterior
    estudiantes[numero - 1] = nuevoEstudiante;

    // Mantener las notas que ya tenía
    double* notasNuevas = estudiantes[numero - 1]->getNotas();
    for (int i = 0; i < 7; i++) {
        notasNuevas[i] = notasAnteriores[i];
    }

    return true;
}

bool RegistroEstudiantes::eliminar(int numero) {
    if (numero < 1 || numero > cantidad) {
        return false;
    }

    delete estudiantes[numero - 1]; // liberar la memoria del estudiante eliminado

    // Desplazar estudiantes hacia la izquierda
    for (int i = numero - 1; i < cantidad - 1; i++) {
        estudiantes[i] = estudiantes[i + 1];
    }

    // Eliminar la última referencia
    estudiantes[cantidad - 1] = nullptr;

    cantidad--;
    return true;
}

void RegistroEstudiantes::listar() const {
    if (cantidad == 0) {
        std::cout << "No hay estudiantes registrados." << std::endl;
        return;
    }

    std::cout << "========== ESTUDIANTES ==========" << std::endl;
    for (int i = 0; i < cantidad; i++) {
        std::cout << "Autonumerico: " << (i + 1) << std::endl;
        estudiantes[i]->mostrarDatos();
    }
}

double RegistroEstudiantes::calcularPromedioCurso() const {
    double suma = 0;
    int cantidadNotas = 0;

    for (int i = 0; i < cantidad; i++) {
        double* notas = estudiantes[i]->getNotas();
        for (int j = 0; j < 7; j++) {
            if (notas[j] != 0) {
                suma += notas[j];
                cantidadNotas++;
            }
        }
    }

    if (cantidadNotas == 0) {
        return 0;
    }
    return suma / cantidadNotas;
}