#ifndef VEHICULO_H
#define VEHICULO_H

#include <string>

// ============================================================
// CONCEPTO: CLASE ABSTRACTA
// Clase padre que contiene los datos comunes de los vehículos.
// Equivalente directo de la clase abstracta Vehiculo en Java.
// ============================================================
class Vehiculo {
protected:
    // CONCEPTO: ENCAPSULAMIENTO
    std::string placa;
    std::string marca;
    std::string modelo;
    int anio;          // dato primitivo (1 de 2 exigidos)
    double precio;      // dato primitivo (2 de 2 exigidos)
    bool disponible;    // dato primitivo adicional

public:
    Vehiculo(const std::string& placa, const std::string& marca,
             const std::string& modelo, int anio, double precio, bool disponible);

    virtual ~Vehiculo() {} // destructor virtual: obligatorio al usar polimorfismo con punteros

    // Obtener la placa
    std::string getPlaca() const;

    // Mostrar los datos comunes
    void mostrarDatosComunes() const;

    // CONCEPTO: POLIMORFISMO
    // En Java: "public abstract void mostrarInformacion();"
    // En C++ el equivalente es una función virtual pura ("= 0"),
    // lo que también vuelve la clase abstracta (no instanciable).
    virtual void mostrarInformacion() const = 0;
};

#endif