#include "Automovil.h"
#include <iostream>

Automovil::Automovil(const std::string& placa, const std::string& marca, const std::string& modelo,
                     int anio, double precio, bool disponible,
                     int numeroPuertas, bool electrico)
    // CONCEPTO: HERENCIA (llamada al constructor de la clase padre, equivale a "super(...)")
    : Vehiculo(placa, marca, modelo, anio, precio, disponible),
      numeroPuertas(numeroPuertas), electrico(electrico) {}

void Automovil::mostrarInformacion() const {
    std::cout << "\n----- AUTOMOVIL -----" << std::endl;
    mostrarDatosComunes();
    std::cout << "Numero de puertas: " << numeroPuertas << std::endl;
    std::cout << "Electrico: " << (electrico ? "Si" : "No") << std::endl;
}