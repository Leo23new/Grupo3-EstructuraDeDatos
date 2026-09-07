// Entidades del juego: Cozy (enemigo, nombre exigido por el enunciado),
// Torre y Oleada. Sin dependencias del DOM: solo datos y comportamiento.

let contadorCozyId = 0;

export class Cozy {
  constructor(tipo, vidaMaxima, velocidadCeldas, recompensa, oro) {
    this.id = ++contadorCozyId;
    this.tipo = tipo;
    this.vidaMaxima = vidaMaxima;
    this.vidaActual = vidaMaxima;
    this.velocidadCeldas = velocidadCeldas; // cuanto avanza por quantum (en celdas de ruta)
    this.recompensa = recompensa; // puntos al morir
    this.oro = oro; // oro que otorga al morir
    this.progresoRuta = 0; // indice fraccionario dentro del arreglo de la ruta
    this.el = null; // referencia al elemento DOM (la asigna main.js)
  }

  recibirDano(dano) {
    this.vidaActual = Math.max(0, this.vidaActual - dano);
  }

  estaMuerto() {
    return this.vidaActual <= 0;
  }

  avanzar() {
    this.progresoRuta += this.velocidadCeldas;
  }

  porcentajeVida() {
    return Math.round((this.vidaActual / this.vidaMaxima) * 100);
  }
}

export class Torre {
  constructor(definicion, fila, columna) {
    this.id = definicion.id;
    this.nombre = definicion.nombre;
    this.emoji = definicion.emoji;
    this.fila = fila;
    this.columna = columna;
    this.costoBase = definicion.costo;
    this.costoMejora = definicion.costoMejora;
    this.dano = definicion.dano;
    this.alcance = definicion.alcance;
    this.nivel = 1;
    this.el = null; // referencia al elemento DOM
  }

  /** Indica si la celda (columna, fila) de un enemigo esta dentro del alcance. */
  enRango(columna, fila) {
    const dx = this.columna - columna;
    const dy = this.fila - fila;
    return Math.sqrt(dx * dx + dy * dy) <= this.alcance;
  }

  mejorar() {
    this.nivel++;
    this.dano = Math.round(this.dano * 1.5);
    this.alcance = Math.round((this.alcance + 0.4) * 100) / 100;
    this.costoMejora = Math.round(this.costoMejora * 1.6);
  }

  restaurar(danoAnterior, alcanceAnterior, nivelAnterior, costoMejoraAnterior) {
    this.dano = danoAnterior;
    this.alcance = alcanceAnterior;
    this.nivel = nivelAnterior;
    this.costoMejora = costoMejoraAnterior;
  }
}

export class Oleada {
  constructor(numero) {
    this.numero = numero;
    this.enemigos = this._generar(numero);
  }

  _generar(numeroOleada) {
    const lista = [];
    const cantidad = 5 + numeroOleada * 2;
    const vidaBase = 25 + numeroOleada * 18;
    const velocidad = +(0.10 + numeroOleada * 0.008).toFixed(3);

    for (let i = 0; i < cantidad; i++) {
      const esJefe = numeroOleada % 3 === 0 && i === cantidad - 1;
      const tipo = esJefe ? "Cozy Jefe" : "Cozy";
      const vida = esJefe ? vidaBase * 5 : vidaBase;
      const recompensa = esJefe ? 40 : 8;
      const oro = esJefe ? 60 : 12;
      lista.push(new Cozy(tipo, vida, velocidad, recompensa, oro));
    }
    return lista;
  }
}
