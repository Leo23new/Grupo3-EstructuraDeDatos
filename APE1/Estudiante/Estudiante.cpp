
#include "Estudiante.h"
#include <iostream>

Estudiante::Estudiante()
    : cedula(""), nombres(""), apellidos(""), fechaNacimiento(Fecha()) {
    for (int i = 0; i < 7; i++) notas[i] = 0;
}

Estudiante::Estudiante(const std::string& cedula, const std::string& nombres,
                       const std::string& apellidos, const Fecha& fechaNacimiento)
    : cedula(cedula), nombres(nombres), apellidos(apellidos),
      fechaNacimiento(fechaNacimiento) {
    // Máximo 7 notas (igual que "this.notas = new double[7];" en Java)
    for (int i = 0; i < 7; i++) notas[i] = 0;
}

std::string Estudiante::getCedula() const { return cedula; }
std::string Estudiante::getNombres() const { return nombres; }
std::string Estudiante::getApellidos() const { return apellidos; }
Fecha Estudiante::getFechaNacimiento() const { return fechaNacimiento; }
double* Estudiante::getNotas() { return notas; }

void Estudiante::setCedula(const std::string& c) { cedula = c; }
void Estudiante::setNombres(const std::string& n) { nombres = n; }
void Estudiante::setApellidos(const std::string& a) { apellidos = a; }
void Estudiante::setFechaNacimiento(const Fecha& f) { fechaNacimiento = f; }

int Estudiante::getEdad() const {
    return calcularEdad(fechaNacimiento);
}

int Estudiante::buscarPosicionLibre() const {
    for (int i = 0; i < 7; i++) {
        if (notas[i] == 0) {
            return i;
        }
    }
    return -1;
}

bool Estudiante::agregarNota(double nota) {
    int posicion = buscarPosicionLibre();
    if (posicion == -1) {
        return false;
    }
    notas[posicion] = nota;
    return true;
}

bool Estudiante::modificarNota(int posicion, double nuevaNota) {
    if (posicion < 0 || posicion >= 7) {
        return false;
    }
    if (notas[posicion] == 0) {
        return false;
    }
    notas[posicion] = nuevaNota;
    return true;
}

bool Estudiante::eliminarNota(int posicion) {
    if (posicion < 0 || posicion >= 7) {
        return false;
    }
    if (notas[posicion] == 0) {
        return false;
    }
    // Mover las notas siguientes hacia atrás
    for (int i = posicion; i < 7 - 1; i++) {
        notas[i] = notas[i + 1];
    }
    notas[7 - 1] = 0;
    return true;
}

int Estudiante::cantidadNotas() const {
    int cantidad = 0;
    for (int i = 0; i < 7; i++) {
        if (notas[i] != 0) {
            cantidad++;
        }
    }
    return cantidad;
}

double Estudiante::calcularPromedio() const {
    double suma = 0;
    int cantidad = 0;
    for (int i = 0; i < 7; i++) {
        if (notas[i] != 0) {
            suma += notas[i];
            cantidad++;
        }
    }
    if (cantidad == 0) {
        return 0;
    }
    return suma / cantidad;
}

void Estudiante::mostrarDatos() const {
    std::cout << "Cedula: " << cedula << std::endl;
    std::cout << "Nombres: " << nombres << std::endl;
    std::cout << "Apellidos: " << apellidos << std::endl;
    std::cout << "Fecha de nacimiento: " << fechaNacimiento << std::endl;
    std::cout << "Edad: " << getEdad() << std::endl;
}