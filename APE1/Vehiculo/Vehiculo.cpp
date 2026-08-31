#include "Vehiculo.h"
#include <iostream>

Vehiculo::Vehiculo(const std::string& placa, const std::string& marca,
                   const std::string& modelo, int anio, double precio, bool disponible)
    : placa(placa), marca(marca), modelo(modelo),
      anio(anio), precio(precio), disponible(disponible) {}

std::string Vehiculo::getPlaca() const {
    return placa;
}

void Vehiculo::mostrarDatosComunes() const {
    std::cout << "Placa: " << placa << std::endl;
    std::cout << "Marca: " << marca << std::endl;
    std::cout << "Modelo: " << modelo << std::endl;
    std::cout << "Año: " << anio << std::endl;
    std::cout << "Precio: $" << precio << std::endl;
    std::cout << "Disponible: " << (disponible ? "Si" : "No") << std::endl;
}