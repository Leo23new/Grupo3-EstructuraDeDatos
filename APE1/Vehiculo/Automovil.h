#ifndef AUTOMOVIL_H
#define AUTOMOVIL_H

#include "Vehiculo.h"

// ============================================================
// CONCEPTO: HERENCIA
// Automovil hereda de Vehiculo (": public Vehiculo" equivale a
// "extends Vehiculo" en Java).
// ============================================================
class Automovil : public Vehiculo {
private:
    // CONCEPTO: ENCAPSULAMIENTO
    const int numeroPuertas;
    const bool electrico;

public:
    Automovil(const std::string& placa, const std::string& marca, const std::string& modelo,
              int anio, double precio, bool disponible,
              int numeroPuertas, bool electrico);

    // CONCEPTO: POLIMORFISMO ("override" equivale a "@Override" en Java)
    void mostrarInformacion() const override;
};

#endif