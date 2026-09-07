// Orquesta la logica principal del juego. No conoce el DOM: simularQuantum()
// devuelve una lista de eventos que main.js usa para animar la interfaz.

import { Cola, ColaCircular } from "./estructuras.js";
import { Oleada } from "./entidades.js";
import { GestorHistorial, ComandoColocarTorre, ComandoMejorarTorre } from "./comandos.js";

const CAPACIDAD_COLA_RUTA = 400;

export class Juego {
  constructor(mapa, vidaInicial, oroInicial, totalOleadas) {
    this.mapa = mapa;
    this.historial = new GestorHistorial();
    this.colaOleadas = new Cola();
    this.colaRuta = new ColaCircular(CAPACIDAD_COLA_RUTA);
    this.torres = [];
    this.celdasOcupadas = new Set();

    this.vida = vidaInicial;
    this.oro = oroInicial;
    this.puntuacion = 0;
    this.numeroOleada = 0;
    this.totalOleadas = totalOleadas;
    this.oleadaEnCurso = false;
    this.terminado = false;
    this.gano = false;

    this.registro = [];

    for (let i = 1; i <= totalOleadas; i++) {
      this.colaOleadas.encolar(new Oleada(i));
    }
  }

  log(mensaje) {
    this.registro.unshift(mensaje);
    if (this.registro.length > 40) this.registro.pop();
  }

  celdaLibre(fila, columna) {
    if (this.mapa.esCeldaDeRuta(fila, columna)) return false;
    return !this.celdasOcupadas.has(`${fila},${columna}`);
  }
  ocuparCelda(fila, columna) {
    this.celdasOcupadas.add(`${fila},${columna}`);
  }
  liberarCelda(fila, columna) {
    this.celdasOcupadas.delete(`${fila},${columna}`);
  }

  puedeColocar(definicion, fila, columna) {
    return this.celdaLibre(fila, columna) && this.oro >= definicion.costo;
  }

  colocarTorre(torre) {
    const comando = new ComandoColocarTorre(this, torre);
    this.historial.ejecutarComando(comando);
    this.log(comando.describir());
  }

  mejorarTorre(torre) {
    if (this.oro < torre.costoMejora) {
      this.log(`Oro insuficiente para mejorar ${torre.nombre}`);
      return false;
    }
    const comando = new ComandoMejorarTorre(this, torre);
    this.historial.ejecutarComando(comando);
    this.log(comando.describir());
    return true;
  }

  deshacer() {
    const ok = this.historial.deshacer();
    this.log(ok ? "Deshacer aplicado" : "No hay nada para deshacer");
    return ok;
  }

  rehacer() {
    const ok = this.historial.rehacer();
    this.log(ok ? "Rehacer aplicado" : "No hay nada para rehacer");
    return ok;
  }

  _iniciarSiguienteOleada() {
    if (this.oleadaEnCurso || this.colaOleadas.estaVacia()) return null;
    const siguiente = this.colaOleadas.desencolar();
    this.numeroOleada = siguiente.numero;
    siguiente.enemigos.forEach((e) => this.colaRuta.encolar(e));
    this.oleadaEnCurso = true;
    this.log(`Comienza la oleada ${this.numeroOleada} (${siguiente.enemigos.length} enemigos)`);
    return siguiente;
  }

  /**
   * Simula un quantum de tiempo: recorre la cola circular UNA vuelta
   * (round-robin) aplicando dano, moviendo y retirando enemigos.
   * Devuelve { eventos } para que la interfaz anime lo ocurrido.
   */
  simularQuantum() {
    if (this.terminado) return { eventos: [] };
    const eventos = [];

    const oleadaIniciada = this._iniciarSiguienteOleada();
    if (oleadaIniciada) {
      eventos.push({ tipo: "oleada-inicio", numero: this.numeroOleada, enemigos: oleadaIniciada.enemigos });
    }

    const enemigosEnEstaVuelta = this.colaRuta.tamano();
    for (let i = 0; i < enemigosEnEstaVuelta; i++) {
      const enemigo = this.colaRuta.desencolar();

      this._aplicarDanoDeTorres(enemigo);

      if (enemigo.estaMuerto()) {
        this.puntuacion += enemigo.recompensa;
        this.oro += enemigo.oro;
        eventos.push({ tipo: "muerte", enemigo });
        continue;
      }

      enemigo.avanzar();

      if (this.mapa.llegoAlFinal(enemigo.progresoRuta)) {
        this.vida = Math.max(0, this.vida - 1);
        eventos.push({ tipo: "fuga", enemigo });
        continue;
      }

      eventos.push({ tipo: "movimiento", enemigo });
      this.colaRuta.encolar(enemigo);
    }

    if (this.oleadaEnCurso && this.colaRuta.estaVacia()) {
      this.oleadaEnCurso = false;
      this.log(`Oleada ${this.numeroOleada} superada`);
      eventos.push({ tipo: "oleada-fin", numero: this.numeroOleada });
    }

    if (this.vida <= 0) {
      this.terminado = true;
      this.gano = false;
      eventos.push({ tipo: "derrota" });
    } else if (this.colaOleadas.estaVacia() && this.colaRuta.estaVacia() && !this.oleadaEnCurso) {
      this.terminado = true;
      this.gano = true;
      eventos.push({ tipo: "victoria" });
    }

    return { eventos };
  }

  _aplicarDanoDeTorres(enemigo) {
    const pos = this.mapa.posicionEnProgreso(enemigo.progresoRuta);
    for (const torre of this.torres) {
      if (torre.enRango(pos.columna, pos.fila)) {
        enemigo.recibirDano(torre.dano);
        if (enemigo.estaMuerto()) break;
      }
    }
  }
}
