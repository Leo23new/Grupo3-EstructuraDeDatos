#ifndef REGISTROVEHICULOS_H
#define REGISTROVEHICULOS_H

#include "Vehiculo.h"

// ============================================================
// CONCEPTO: TDA REGISTRO DE VEHICULOS
// Administra el arreglo de vehículos.
// Usa Vehiculo* (puntero a la clase base) para que el arreglo
// pueda almacenar tanto Automovil como Motocicleta y despachar
// mostrarInformacion() de forma polimórfica, igual que en Java.
// ============================================================
class RegistroVehiculos {
private:
    // CONCEPTO: ARREGLO DE TAMAÑO FIJO
    static const int CAPACIDAD = 10;
    Vehiculo* vehiculos[CAPACIDAD];

    // CONCEPTO: DATO PRIMITIVO
    int cantidad;

public:
    RegistroVehiculos();
    ~RegistroVehiculos(); // libera la memoria de los vehículos registrados

    // Registrar un vehículo
    bool registrar(Vehiculo* nuevoVehiculo);

    // CONCEPTO: POLIMORFISMO
    void mostrarTodos() const;

    // Comprobar si existe una placa
    bool existePlaca(const std::string& placa) const;

    // Comprobar si el arreglo está lleno
    bool estaLleno() const;

    // Obtener cantidad de vehículos
    int getCantidad() const;
};

#endif