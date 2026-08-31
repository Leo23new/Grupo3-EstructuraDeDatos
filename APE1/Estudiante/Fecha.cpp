#include "Fecha.h"
#include <ctime>
#include <sstream>
#include <iomanip>
#include <cstdio>

std::string Fecha::toString() const {
    char buf[11];
    snprintf(buf, sizeof(buf), "%04d-%02d-%02d", anio, mes, dia);
    return std::string(buf);
}

std::ostream& operator<<(std::ostream& os, const Fecha& f) {
    os << f.toString();
    return os;
}

static bool esBisiesto(int anio) {
    return (anio % 4 == 0 && anio % 100 != 0) || (anio % 400 == 0);
}

static int diasDelMes(int mes, int anio) {
    static const int dias[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    if (mes == 2 && esBisiesto(anio)) return 29;
    if (mes < 1 || mes > 12) return 0;
    return dias[mes - 1];
}

bool parsearFecha(const std::string& texto, Fecha& resultado) {
    int a, m, d;
    // Formato estricto AAAA-MM-DD, igual al que exige LocalDate.parse
    if (sscanf(texto.c_str(), "%d-%d-%d", &a, &m, &d) != 3) {
        return false;
    }
    if (texto.size() != 10 || texto[4] != '-' || texto[7] != '-') {
        return false;
    }
    if (m < 1 || m > 12) return false;
    if (d < 1 || d > diasDelMes(m, a)) return false;

    resultado = Fecha(a, m, d);
    return true;
}

Fecha fechaActual() {
    time_t ahora = time(nullptr);
    tm* local = localtime(&ahora);
    return Fecha(local->tm_year + 1900, local->tm_mon + 1, local->tm_mday);
}

int calcularEdad(const Fecha& nacimiento) {
    Fecha hoy = fechaActual();
    int edad = hoy.anio - nacimiento.anio;
    if (hoy.mes < nacimiento.mes ||
        (hoy.mes == nacimiento.mes && hoy.dia < nacimiento.dia)) {
        edad--;
    }
    return edad;
}