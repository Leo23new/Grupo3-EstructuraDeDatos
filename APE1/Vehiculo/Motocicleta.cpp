#include "Motocicleta.h"
#include <iostream>

Motocicleta::Motocicleta(const std::string& placa, const std::string& marca, const std::string& modelo,
                         int anio, double precio, bool disponible,
                         int cilindrada, bool tieneMaletero)
    : Vehiculo(placa, marca, modelo, anio, precio, disponible),
      cilindrada(cilindrada), tieneMaletero(tieneMaletero) {}

void Motocicleta::mostrarInformacion() const {
    std::cout << "\n----- MOTOCICLETA -----" << std::endl;
    mostrarDatosComunes();
    std::cout << "Cilindrada: " << cilindrada << " cc" << std::endl;
    std::cout << "Tiene maletero: " << (tieneMaletero ? "Si" : "No") << std::endl;
}