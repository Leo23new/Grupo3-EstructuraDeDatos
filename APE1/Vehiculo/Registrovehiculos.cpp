#include "RegistroVehiculos.h"
#include <iostream>

RegistroVehiculos::RegistroVehiculos() : cantidad(0) {
    for (int i = 0; i < CAPACIDAD; i++) vehiculos[i] = nullptr;
}

RegistroVehiculos::~RegistroVehiculos() {
    for (int i = 0; i < cantidad; i++) {
        delete vehiculos[i];
    }
}

bool RegistroVehiculos::registrar(Vehiculo* nuevoVehiculo) {
    if (nuevoVehiculo == nullptr) {
        return false;
    }
    if (estaLleno()) {
        return false;
    }
    if (existePlaca(nuevoVehiculo->getPlaca())) {
        return false;
    }
    vehiculos[cantidad] = nuevoVehiculo;
    cantidad++;
    return true;
}

void RegistroVehiculos::mostrarTodos() const {
    if (cantidad == 0) {
        std::cout << "\nNo hay vehiculos registrados." << std::endl;
        return;
    }

    std::cout << "\n================================" << std::endl;
    std::cout << "       VEHICULOS REGISTRADOS" << std::endl;
    std::cout << "================================" << std::endl;

    for (int i = 0; i < cantidad; i++) {
        // C++ ejecuta el método correspondiente al objeto real
        // (Automovil o Motocicleta) gracias a que mostrarInformacion()
        // es virtual: esto es el polimorfismo en tiempo de ejecución.
        vehiculos[i]->mostrarInformacion();
        std::cout << "-------------------------------" << std::endl;
    }
}

bool RegistroVehiculos::existePlaca(const std::string& placa) const {
    for (int i = 0; i < cantidad; i++) {
        std::string placaActual = vehiculos[i]->getPlaca();
        if (placaActual.size() == placa.size()) {
            bool iguales = true;
            for (size_t j = 0; j < placa.size(); j++) {
                if (tolower(placaActual[j]) != tolower(placa[j])) {
                    iguales = false;
                    break;
                }
            }
            if (iguales) return true;
        }
    }
    return false;
}

bool RegistroVehiculos::estaLleno() const {
    return cantidad == CAPACIDAD;
}

int RegistroVehiculos::getCantidad() const {
    return cantidad;
}