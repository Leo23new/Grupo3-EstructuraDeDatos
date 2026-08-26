#include <iostream>
#include "Persona.h"
#include "Objeto.h"
#include "Cadenadetrasnsmicion.h"

int main() {
    Persona persona1("Juan", false, false);
    Persona persona2("Maria", true, true);
    Objeto objeto1("Mesa", false);
    Objeto objeto2("Silla", true);

    Cadenadetrasnsmicion cadena;

    std::cout << cadena.estornudarSobreObjeto(persona1, objeto1) << std::endl;
    std::cout << cadena.tocarObjeto(persona2, objeto2) << std::endl;
    std::cout << cadena.tocarRostro(persona1) << std::endl;

    persona1.mostrarEstado();
    persona2.mostrarEstado();
    objeto1.mostrarEstado();
    objeto2.mostrarEstado();

    return 0;
}