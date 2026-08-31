#ifndef ESTUDIANTE_H
#define ESTUDIANTE_H

#include <string>
#include "Fecha.h"

/**
 * Equivalente directo de Estudiante.java.
 * Mismos atributos, mismo vector de notas de tamaño fijo (7),
 * y los mismos métodos con el mismo comportamiento.
 */
class Estudiante {
private:
    std::string cedula;
    std::string nombres;
    std::string apellidos;
    Fecha fechaNacimiento;

    // Vector para máximo 7 notas (igual que en Java)
    double notas[7];

public:
    Estudiante(); // constructor por defecto (requerido para el arreglo estático en RegistroEstudiantes)
    Estudiante(const std::string& cedula, const std::string& nombres,
               const std::string& apellidos, const Fecha& fechaNacimiento);

    // Getters
    std::string getCedula() const;
    std::string getNombres() const;
    std::string getApellidos() const;
    Fecha getFechaNacimiento() const;
    double* getNotas(); // devuelve el arreglo interno, igual que Java (permite modificarlo desde fuera)

    // Setters
    void setCedula(const std::string& cedula);
    void setNombres(const std::string& nombres);
    void setApellidos(const std::string& apellidos);
    void setFechaNacimiento(const Fecha& fechaNacimiento);

    // Calcular edad
    int getEdad() const;

    // Buscar una posición libre en el vector de notas
    int buscarPosicionLibre() const;

    // Agregar nota
    bool agregarNota(double nota);

    // Modificar nota
    bool modificarNota(int posicion, double nuevaNota);

    // Eliminar nota
    bool eliminarNota(int posicion);

    // Cantidad de notas registradas
    int cantidadNotas() const;

    // Calcular promedio del estudiante
    double calcularPromedio() const;

    // Mostrar datos
    void mostrarDatos() const;
};

#endif