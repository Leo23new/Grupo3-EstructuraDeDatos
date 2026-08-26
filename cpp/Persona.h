#ifndef PERSONA_H
#define PERSONA_H
#include <string>

class Persona {
private:
    std::string nombre;
    bool infectado;
    bool manosContaminadas;

public:
    Persona(std::string nombre, bool infectado, bool manosContaminadas);
    std::string getNombre();
    bool isInfectado();
    bool isManosContaminadas();
    void lavarManos();
    void contaminar();
    void tocarRostro();
    void mostrarEstado();
};

#endif