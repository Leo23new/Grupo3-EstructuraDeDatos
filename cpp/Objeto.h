#ifndef OBJETO_H
#define OBJETO_H
#include <string>

class Objeto {
private:
    std::string nombre;
    bool infectado;

public:
    Objeto(std::string nombre, bool infectado);
    std::string getNombre();
    bool isInfectado();
    void contaminar();
    void limpiar();
    void mostrarEstado();
};

#endif