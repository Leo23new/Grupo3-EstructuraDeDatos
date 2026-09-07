// Representa el mapa: la ruta predefinida (entrada -> fuente de energia)
// como una lista de celdas {fila, columna}, y utilidades para ubicar
// enemigos a lo largo de ella con interpolacion suave.

export class Mapa {
  constructor(ruta, filas, columnas) {
    this.ruta = ruta;
    this.filas = filas;
    this.columnas = columnas;
    this._clavesRuta = new Set(ruta.map((p) => `${p.fila},${p.columna}`));
  }

  longitudRuta() {
    return this.ruta.length;
  }

  esCeldaDeRuta(fila, columna) {
    return this._clavesRuta.has(`${fila},${columna}`);
  }

  /** Posicion (fila, columna) interpolada segun el progreso fraccional [0, longitud-1]. */
  posicionEnProgreso(progreso) {
    const ultimo = this.ruta.length - 1;
    if (progreso >= ultimo) {
      const p = this.ruta[ultimo];
      return { fila: p.fila, columna: p.columna };
    }
    const i = Math.floor(progreso);
    const frac = progreso - i;
    const a = this.ruta[i];
    const b = this.ruta[i + 1];
    return {
      fila: a.fila + (b.fila - a.fila) * frac,
      columna: a.columna + (b.columna - a.columna) * frac,
    };
  }

  llegoAlFinal(progreso) {
    return progreso >= this.ruta.length - 1;
  }
}
