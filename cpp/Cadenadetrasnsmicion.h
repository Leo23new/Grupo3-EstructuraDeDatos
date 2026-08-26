#ifndef CADENADETRASNSMICION_H
#define CADENADETRASNSMICION_H
#include <string>
#include "Persona.h"
#include "Objeto.h"

class Cadenadetrasnsmicion {
public:
    std::string estornudarSobreObjeto(Persona &persona, Objeto &objeto);
    std::string tocarObjeto(Persona &persona, Objeto &objeto);
    std::string tocarRostro(Persona &persona);
};

#endif