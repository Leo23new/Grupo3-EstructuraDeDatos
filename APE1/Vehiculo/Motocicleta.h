#ifndef MOTOCICLETA_H
#define MOTOCICLETA_H

#include "Vehiculo.h"

// ============================================================
// CONCEPTO: HERENCIA
// Motocicleta hereda de Vehiculo.
// ============================================================
class Motocicleta : public Vehiculo {
private:
    const int cilindrada;
    const bool tieneMaletero;

public:
    Motocicleta(const std::string& placa, const std::string& marca, const std::string& modelo,
                int anio, double precio, bool disponible,
                int cilindrada, bool tieneMaletero);

    // CONCEPTO: POLIMORFISMO
    void mostrarInformacion() const override;
};

#endif