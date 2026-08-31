#include "Persona.h"
#include <iostream>

Persona::Persona(std::string nombre, bool infectado, bool manosContaminadas) {
    this->nombre = nombre;
    this->infectado = infectado;
    this->manosContaminadas = manosContaminadas;
}

std::string Persona::getNombre() {
    return nombre;
}

bool Persona::isInfectado() {
    return infectado;
}

bool Persona::isManosContaminadas() {
    return manosContaminadas;
}

void Persona::lavarManos() {
    manosContaminadas = false;
    std::cout << nombre << " se ha lavado las manos." << std::endl;
    std::cout << "Las manos de " << nombre << " ya no estan contaminadas." << std::endl;
}

void Persona::contaminar() {
    manosContaminadas = true;
    std::cout << nombre << " se ha contaminado las manos." << std::endl;
    std::cout << "Las manos de " << nombre << " estan contaminadas." << std::endl;
}

void Persona::tocarRostro() {
    std::cout << nombre << " se ha tocado el rostro." << std::endl;
    if (manosContaminadas) {
        infectado = true;
        std::cout << nombre << " se ha infectado al tocarse el rostro con las manos contaminadas." << std::endl;
    } else {
        std::cout << nombre << " se ha tocado el rostro sin las manos contaminadas." << std::endl;
    }
}

void Persona::mostrarEstado() {
    std::cout << "Nombre: " << nombre << std::endl;
    std::cout << "Infectado: " << (infectado ? "true" : "false") << std::endl;
    std::cout << "Manos contaminadas: " << (manosContaminadas ? "true" : "false") << std::endl;
}