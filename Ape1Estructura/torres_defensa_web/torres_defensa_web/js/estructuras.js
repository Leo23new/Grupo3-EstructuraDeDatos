// Estructuras de datos genericas usadas por el juego.
// Son el equivalente en JavaScript de Cola.java, ColaCircular.java y Pila.java.

/** Cola FIFO simple. Se usa para el orden en que se disparan las oleadas. */
export class Cola {
  constructor() {
    this._items = [];
  }
  encolar(item) {
    this._items.push(item);
  }
  desencolar() {
    if (this.estaVacia()) throw new Error("La cola esta vacia");
    return this._items.shift();
  }
  verFrente() {
    if (this.estaVacia()) throw new Error("La cola esta vacia");
    return this._items[0];
  }
  estaVacia() {
    return this._items.length === 0;
  }
  tamano() {
    return this._items.length;
  }
}

/**
 * Cola circular de capacidad fija. Se usa para procesar a los Cozy activos
 * en la ruta con logica round-robin: cada quantum se recorre una vuelta
 * completa (tamano() elementos), y cada enemigo se vuelve a encolar al
 * final solo si sigue vivo y no ha llegado a la fuente de energia.
 */
export class ColaCircular {
  constructor(capacidad) {
    this.capacidad = capacidad;
    this.datos = new Array(capacidad).fill(null);
    this.frente = 0;
    this.fin = -1;
    this.cantidad = 0;
  }
  estaLlena() {
    return this.cantidad === this.capacidad;
  }
  estaVacia() {
    return this.cantidad === 0;
  }
  tamano() {
    return this.cantidad;
  }
  encolar(elemento) {
    if (this.estaLlena()) throw new Error("La cola circular esta llena");
    this.fin = (this.fin + 1) % this.capacidad;
    this.datos[this.fin] = elemento;
    this.cantidad++;
  }
  desencolar() {
    if (this.estaVacia()) throw new Error("La cola circular esta vacia");
    const dato = this.datos[this.frente];
    this.datos[this.frente] = null;
    this.frente = (this.frente + 1) % this.capacidad;
    this.cantidad--;
    return dato;
  }
  verFrente() {
    if (this.estaVacia()) throw new Error("La cola circular esta vacia");
    return this.datos[this.frente];
  }
}

/** Pila LIFO simple. Se usan dos (deshacer/rehacer) dentro de GestorHistorial. */
export class Pila {
  constructor() {
    this._items = [];
  }
  apilar(item) {
    this._items.push(item);
  }
  desapilar() {
    if (this.estaVacia()) throw new Error("La pila esta vacia");
    return this._items.pop();
  }
  verCima() {
    if (this.estaVacia()) throw new Error("La pila esta vacia");
    return this._items[this._items.length - 1];
  }
  estaVacia() {
    return this._items.length === 0;
  }
  tamano() {
    return this._items.length;
  }
  vaciar() {
    this._items = [];
  }
}
