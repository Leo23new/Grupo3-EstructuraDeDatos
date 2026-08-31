#ifndef REGISTROESTUDIANTES_H
#define REGISTROESTUDIANTES_H

#include "Estudiante.h"

/**
 * Equivalente directo de RegistroEstudiantes.java.
 * Usa un arreglo estático de PUNTEROS a Estudiante (Estudiante*[20])
 * en lugar de un arreglo de objetos, porque eso es lo que realmente
 * imita el comportamiento de "Estudiante[] estudiantes" en Java:
 * cada casilla es una referencia que puede reasignarse o quedar
 * vacía (nullptr equivale al "null" de Java), y varias posiciones
 * distintas del arreglo pueden apuntar al mismo objeto real.
 */
class RegistroEstudiantes {
private:
    static const int MAX_ESTUDIANTES = 20;
    Estudiante* estudiantes[MAX_ESTUDIANTES];
    int cantidad;

public:
    RegistroEstudiantes();
    ~RegistroEstudiantes(); // libera la memoria de los estudiantes restantes

    int getCantidad() const;
    bool estaLleno() const;

    // Registrar estudiante
    bool registrar(Estudiante* estudiante);

    // Buscar por cedula (retorna nullptr si no existe, equivalente a null)
    Estudiante* buscarPorCedula(const std::string& cedula);

    // Buscar por autonumérico (posición 1-based mostrada al usuario)
    Estudiante* buscarPorAutonumerico(int numero);

    // Modificar estudiante
    bool modificar(int numero, Estudiante* nuevoEstudiante);

    // Eliminar estudiante
    bool eliminar(int numero);

    // Listar estudiantes
    void listar() const;

    // Calcular promedio general del curso
    double calcularPromedioCurso() const;
};

#endif