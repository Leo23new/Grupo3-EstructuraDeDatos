#include "Cadenadetrasnsmicion.h"

std::string Cadenadetrasnsmicion::estornudarSobreObjeto(Persona &persona, Objeto &objeto) {
    if (!persona.isInfectado()) {
        return persona.getNombre() + " no esta infectado y " + objeto.getNombre() + " estaba limpio.";
    }

    objeto.contaminar();
    return persona.getNombre() + " ha estornudado sobre " + objeto.getNombre() + ", contaminandolo.";
}

std::string Cadenadetrasnsmicion::tocarObjeto(Persona &persona, Objeto &objeto) {
    if (!objeto.isInfectado()) {
        persona.contaminar();
        return persona.getNombre() + " ha tocado " + objeto.getNombre() + ", contaminandose.";
    } else {
        return persona.getNombre() + " ha tocado " + objeto.getNombre() + ", pero no se ha contaminado.";
    }
}

std::string Cadenadetrasnsmicion::tocarRostro(Persona &persona) {
    persona.tocarRostro();

    if (persona.isInfectado()) {
        return persona.getNombre() + " se ha tocado el rostro y se ha infectado.";
    } else {
        return persona.getNombre() + " se ha tocado el rostro pero no se ha infectado.";
    }
}