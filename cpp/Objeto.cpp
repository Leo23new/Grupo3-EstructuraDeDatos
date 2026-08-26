#include "Objeto.h"
#include <iostream>

Objeto::Objeto(std::string nombre, bool infectado) {
    this->nombre = nombre;
    this->infectado = infectado;
}

std::string Objeto::getNombre() {
    return nombre;
}

bool Objeto::isInfectado() {
    return infectado;
}

void Objeto::contaminar() {
    infectado = true;
    std::cout << nombre << " se ha contaminado." << std::endl;
    std::cout << nombre << " esta contaminado." << std::endl;
}

void Objeto::limpiar() {
    infectado = false;
    std::cout << nombre << " se ha limpiado." << std::endl;
    std::cout << nombre << " ya no esta contaminado." << std::endl;
}

void Objeto::mostrarEstado() {
    std::cout << "Nombre: " << nombre << std::endl;
    std::cout << "Infectado: " << (infectado ? "true" : "false") << std::endl;
}