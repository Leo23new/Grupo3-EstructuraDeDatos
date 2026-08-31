#ifndef FECHA_H
#define FECHA_H

#include <string>
#include <iostream>

/**
 * Equivalente simplificado de java.time.LocalDate.
 * Java trae LocalDate/Period incorporados; en C++ no existe un tipo
 * de fecha estándar tan directo, así que se modela aquí con una
 * estructura simple (año, mes, día) más las funciones necesarias
 * para parsear, mostrar y calcular la edad, replicando el
 * comportamiento de LocalDate.parse(...) y Period.between(...).
 */
struct Fecha {
    int anio;
    int mes;
    int dia;

    Fecha() : anio(1900), mes(1), dia(1) {}
    Fecha(int a, int m, int d) : anio(a), mes(m), dia(d) {}

    std::string toString() const;
};

// Analogo a LocalDate.parse("AAAA-MM-DD"): devuelve true si el
// formato y el rango de la fecha son válidos.
bool parsearFecha(const std::string& texto, Fecha& resultado);

// Analogo a LocalDate.now()
Fecha fechaActual();

// Analogo a Period.between(fechaNacimiento, LocalDate.now()).getYears()
int calcularEdad(const Fecha& nacimiento);

std::ostream& operator<<(std::ostream& os, const Fecha& f);

#endif