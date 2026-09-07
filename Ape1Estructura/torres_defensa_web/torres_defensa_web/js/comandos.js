// Patron Comando para colocar/mejorar torres, y el gestor de historial
// que usa dos Pilas (deshacer/rehacer), igual que en la version Java.

import { Pila } from "./estructuras.js";

export class ComandoTorre {
  ejecutar() {}
  deshacer() {}
  describir() {
    return "";
  }
}

export class ComandoColocarTorre extends ComandoTorre {
  constructor(juego, torre) {
    super();
    this.juego = juego;
    this.torre = torre;
  }
  ejecutar() {
    this.juego.torres.push(this.torre);
    this.juego.oro -= this.torre.costoBase;
    this.juego.ocuparCelda(this.torre.fila, this.torre.columna);
  }
  deshacer() {
    const idx = this.juego.torres.indexOf(this.torre);
    if (idx >= 0) this.juego.torres.splice(idx, 1);
    this.juego.oro += this.torre.costoBase;
    this.juego.liberarCelda(this.torre.fila, this.torre.columna);
  }
  describir() {
    return `Colocar ${this.torre.nombre} en (${this.torre.fila}, ${this.torre.columna})`;
  }
}

export class ComandoMejorarTorre extends ComandoTorre {
  constructor(juego, torre) {
    super();
    this.juego = juego;
    this.torre = torre;
    this.danoAnterior = 0;
    this.alcanceAnterior = 0;
    this.nivelAnterior = 0;
    this.costoMejoraAnterior = 0;
  }
  ejecutar() {
    this.danoAnterior = this.torre.dano;
    this.alcanceAnterior = this.torre.alcance;
    this.nivelAnterior = this.torre.nivel;
    this.costoMejoraAnterior = this.torre.costoMejora;
    this.juego.oro -= this.torre.costoMejora;
    this.torre.mejorar();
  }
  deshacer() {
    this.juego.oro += this.torre.costoMejora;
    this.torre.restaurar(
      this.danoAnterior,
      this.alcanceAnterior,
      this.nivelAnterior,
      this.costoMejoraAnterior
    );
  }
  describir() {
    return `Mejorar ${this.torre.nombre} a nivel ${this.nivelAnterior + 1}`;
  }
}

export class GestorHistorial {
  constructor() {
    this.pilaDeshacer = new Pila();
    this.pilaRehacer = new Pila();
  }

  ejecutarComando(comando) {
    comando.ejecutar();
    this.pilaDeshacer.apilar(comando);
    this.pilaRehacer.vaciar(); // una accion nueva invalida el "rehacer"
  }

  deshacer() {
    if (this.pilaDeshacer.estaVacia()) return false;
    const comando = this.pilaDeshacer.desapilar();
    comando.deshacer();
    this.pilaRehacer.apilar(comando);
    return true;
  }

  rehacer() {
    if (this.pilaRehacer.estaVacia()) return false;
    const comando = this.pilaRehacer.desapilar();
    comando.ejecutar();
    this.pilaDeshacer.apilar(comando);
    return true;
  }

  hayParaDeshacer() {
    return !this.pilaDeshacer.estaVacia();
  }
  hayParaRehacer() {
    return !this.pilaRehacer.estaVacia();
  }
}
