import { Mapa } from "./mapa.js";
import { Juego } from "./juego.js";
import { Torre } from "./entidades.js";

// ----------------------------------------------------------------------
// Configuracion del mapa: cuadricula y ruta predefinida (entrada -> fuente)
// ----------------------------------------------------------------------
const FILAS = 8;
const COLUMNAS = 14;
const CELL = 54; // debe coincidir con --cell en estilos.css

const RUTA = [
  { fila: 3, columna: 0 }, { fila: 3, columna: 1 }, { fila: 3, columna: 2 }, { fila: 3, columna: 3 },
  { fila: 2, columna: 3 }, { fila: 1, columna: 3 },
  { fila: 1, columna: 4 }, { fila: 1, columna: 5 }, { fila: 1, columna: 6 },
  { fila: 2, columna: 6 }, { fila: 3, columna: 6 }, { fila: 4, columna: 6 }, { fila: 5, columna: 6 },
  { fila: 5, columna: 7 }, { fila: 5, columna: 8 },
  { fila: 4, columna: 8 }, { fila: 3, columna: 8 }, { fila: 2, columna: 8 }, { fila: 1, columna: 8 },
  { fila: 1, columna: 9 }, { fila: 1, columna: 10 }, { fila: 1, columna: 11 }, { fila: 1, columna: 12 }, { fila: 1, columna: 13 },
];

const DEFINICIONES_TORRES = [
  { id: "arquero", nombre: "Arquero", emoji: "🏹", dano: 8, alcance: 2.3, costo: 40, costoMejora: 30 },
  { id: "canon", nombre: "Cañón", emoji: "💥", dano: 20, alcance: 1.8, costo: 80, costoMejora: 60 },
  { id: "mago", nombre: "Mago", emoji: "🔮", dano: 13, alcance: 2.8, costo: 70, costoMejora: 50 },
];

const VIDA_INICIAL = 20;
const ORO_INICIAL = 150;
const TOTAL_OLEADAS = 6;

// ----------------------------------------------------------------------
// Referencias al DOM
// ----------------------------------------------------------------------
const elCapaCeldas = document.getElementById("capa-celdas");
const elCapaEntidades = document.getElementById("capa-entidades");
const elListaTorres = document.getElementById("lista-torres");
const elRegistro = document.getElementById("registro");
const elMensaje = document.getElementById("mensaje");

const elValorVida = document.getElementById("valor-vida");
const elValorOro = document.getElementById("valor-oro");
const elValorPuntos = document.getElementById("valor-puntos");
const elValorOleada = document.getElementById("valor-oleada");

const btnDeshacer = document.getElementById("btn-deshacer");
const btnRehacer = document.getElementById("btn-rehacer");
const btnModoColocar = document.getElementById("btn-modo-colocar");
const btnModoMejorar = document.getElementById("btn-modo-mejorar");
const btnIniciarOleada = document.getElementById("btn-iniciar-oleada");
const btnPausa = document.getElementById("btn-pausa");
const btnVelocidad = document.getElementById("btn-velocidad");
const btnReiniciar = document.getElementById("btn-reiniciar");

const overlayFinal = document.getElementById("overlay-final");
const tituloFinal = document.getElementById("titulo-final");
const detalleFinal = document.getElementById("detalle-final");
const btnJugarDeNuevo = document.getElementById("btn-jugar-de-nuevo");

// ----------------------------------------------------------------------
// Estado de la aplicacion
// ----------------------------------------------------------------------
let mapa, juego;
let torreSeleccionadaId = DEFINICIONES_TORRES[0].id;
let modo = "colocar"; // 'colocar' | 'mejorar'
let quantumBaseMs = 400;
let velocidadFactor = 1;
let intervaloId = null;
let pausado = false;
let previewRango = null;

function celdaAPixeles(fila, columna) {
  return { left: columna * CELL + CELL / 2, top: fila * CELL + CELL / 2 };
}

// ----------------------------------------------------------------------
// Construccion del tablero
// ----------------------------------------------------------------------
function construirTablero() {
  elCapaCeldas.innerHTML = "";
  for (let f = 0; f < FILAS; f++) {
    for (let c = 0; c < COLUMNAS; c++) {
      const celda = document.createElement("div");
      celda.className = "celda";
      celda.dataset.fila = f;
      celda.dataset.columna = c;

      if (mapa.esCeldaDeRuta(f, c)) {
        celda.classList.add("ruta");
        if (f === RUTA[0].fila && c === RUTA[0].columna) celda.classList.add("entrada");
        const ult = RUTA[RUTA.length - 1];
        if (f === ult.fila && c === ult.columna) celda.classList.add("fuente");
      } else {
        celda.classList.add("construible");
        celda.addEventListener("click", () => alClicCelda(f, c));
        celda.addEventListener("mouseenter", () => alEntrarCelda(f, c));
        celda.addEventListener("mouseleave", ocultarPreview);
      }
      elCapaCeldas.appendChild(celda);
    }
  }
}

// ----------------------------------------------------------------------
// Panel de seleccion de torres
// ----------------------------------------------------------------------
function construirPanelTorres() {
  elListaTorres.innerHTML = "";
  DEFINICIONES_TORRES.forEach((def) => {
    const opcion = document.createElement("div");
    opcion.className = "torre-opcion";
    opcion.dataset.id = def.id;
    opcion.innerHTML = `
      <span class="emoji">${def.emoji}</span>
      <span class="info">
        <span class="nombre">${def.nombre}</span>
        <span class="detalle">DD ${def.dano} · Alcance ${def.alcance} · 💰${def.costo}</span>
      </span>`;
    opcion.addEventListener("click", () => {
      torreSeleccionadaId = def.id;
      cambiarModo("colocar");
      actualizarSeleccionTorres();
    });
    elListaTorres.appendChild(opcion);
  });
  actualizarSeleccionTorres();
}

function actualizarSeleccionTorres() {
  const opciones = elListaTorres.querySelectorAll(".torre-opcion");
  opciones.forEach((op) => {
    const def = DEFINICIONES_TORRES.find((d) => d.id === op.dataset.id);
    op.classList.toggle("seleccionada", op.dataset.id === torreSeleccionadaId && modo === "colocar");
    op.classList.toggle("sin-oro", juego.oro < def.costo);
  });
}

// ----------------------------------------------------------------------
// Interaccion: colocar / mejorar / preview de alcance
// ----------------------------------------------------------------------
function cambiarModo(nuevoModo) {
  modo = nuevoModo;
  btnModoColocar.classList.toggle("activo", modo === "colocar");
  btnModoMejorar.classList.toggle("activo", modo === "mejorar");
  document.querySelectorAll(".torre").forEach((el) => el.classList.toggle("mejorable", modo === "mejorar"));
  actualizarSeleccionTorres();
}

function alClicCelda(fila, columna) {
  if (juego.terminado) return;

  if (modo === "colocar") {
    const def = DEFINICIONES_TORRES.find((d) => d.id === torreSeleccionadaId);
    if (!juego.celdaLibre(fila, columna)) {
      mostrarMensaje("Esa celda ya esta ocupada o es parte de la ruta.");
      return;
    }
    if (juego.oro < def.costo) {
      mostrarMensaje(`Oro insuficiente para ${def.nombre} (necesitas 💰${def.costo}).`);
      return;
    }
    const torre = new Torre(def, fila, columna);
    juego.colocarTorre(torre);
    crearElementoTorre(torre);
    mostrarMensaje(`${def.nombre} colocada en (${fila}, ${columna}).`);
    actualizarUI();
  }
}

function alEntrarCelda(fila, columna) {
  if (modo !== "colocar" || juego.terminado) return;
  const def = DEFINICIONES_TORRES.find((d) => d.id === torreSeleccionadaId);
  mostrarPreview(fila, columna, def.alcance, juego.celdaLibre(fila, columna) && juego.oro >= def.costo);
}

function mostrarPreview(fila, columna, alcanceCeldas, valido) {
  ocultarPreview();
  const { left, top } = celdaAPixeles(fila, columna);
  const diametro = alcanceCeldas * 2 * CELL;
  previewRango = document.createElement("div");
  previewRango.className = "rango-preview";
  previewRango.style.left = `${left}px`;
  previewRango.style.top = `${top}px`;
  previewRango.style.width = `${diametro}px`;
  previewRango.style.height = `${diametro}px`;
  previewRango.style.borderColor = valido ? "var(--acento)" : "var(--peligro)";
  elCapaEntidades.appendChild(previewRango);
}
function ocultarPreview() {
  if (previewRango) {
    previewRango.remove();
    previewRango = null;
  }
}

// ----------------------------------------------------------------------
// Elementos visuales de torres y enemigos
// ----------------------------------------------------------------------
function crearElementoTorre(torre) {
  const { left, top } = celdaAPixeles(torre.fila, torre.columna);
  const el = document.createElement("div");
  el.className = "torre" + (modo === "mejorar" ? " mejorable" : "");
  el.style.left = `${left}px`;
  el.style.top = `${top}px`;
  el.innerHTML = `${torre.emoji}<span class="nivel">Nv${torre.nivel}</span>`;
  el.addEventListener("click", () => alClicTorre(torre));
  torre.el = el;
  elCapaEntidades.appendChild(el);
}

function alClicTorre(torre) {
  if (modo !== "mejorar" || juego.terminado) return;
  const okAntesDeIntentar = juego.oro >= torre.costoMejora;
  const ok = juego.mejorarTorre(torre);
  if (ok) {
    torre.el.querySelector(".nivel").textContent = `Nv${torre.nivel}`;
    mostrarMensaje(`${torre.nombre} mejorada a nivel ${torre.nivel}.`);
  } else if (!okAntesDeIntentar) {
    mostrarMensaje(`Oro insuficiente para mejorar ${torre.nombre} (necesitas 💰${torre.costoMejora}).`);
  }
  actualizarUI();
}

/** Reconstruye todos los elementos de torres desde juego.torres (usado tras deshacer/rehacer). */
function resincronizarTorres() {
  elCapaEntidades.querySelectorAll(".torre").forEach((el) => el.remove());
  juego.torres.forEach(crearElementoTorre);
}

function crearElementoEnemigo(enemigo) {
  const pos = mapa.posicionEnProgreso(enemigo.progresoRuta);
  const { left, top } = celdaAPixeles(pos.fila, pos.columna);
  const el = document.createElement("div");
  el.className = "enemigo sin-transicion" + (enemigo.tipo === "Cozy Jefe" ? " jefe" : "");
  el.style.left = `${left}px`;
  el.style.top = `${top}px`;
  el.innerHTML = `
    <span>${enemigo.tipo === "Cozy Jefe" ? "🐗" : "🐹"}</span>
    <span class="barra-vida"><span class="relleno" style="width:100%"></span></span>`;
  enemigo.el = el;
  elCapaEntidades.appendChild(el);
  // quita la clase "sin-transicion" en el siguiente frame para que futuros
  // cambios de posicion si se animen suavemente
  requestAnimationFrame(() => el.classList.remove("sin-transicion"));
}

function actualizarPosicionEnemigo(enemigo) {
  if (!enemigo.el) return;
  const pos = mapa.posicionEnProgreso(enemigo.progresoRuta);
  const { left, top } = celdaAPixeles(pos.fila, pos.columna);
  enemigo.el.style.left = `${left}px`;
  enemigo.el.style.top = `${top}px`;
  const relleno = enemigo.el.querySelector(".relleno");
  if (relleno) relleno.style.width = `${enemigo.porcentajeVida()}%`;
}

function eliminarEnemigo(enemigo, motivo) {
  if (!enemigo.el) return;
  enemigo.el.classList.add(motivo === "muerte" ? "muerte" : "fuga");
  const elRef = enemigo.el;
  setTimeout(() => elRef.remove(), 380);
  enemigo.el = null;
}

// ----------------------------------------------------------------------
// UI general: stats, registro, botones
// ----------------------------------------------------------------------
function actualizarUI() {
  elValorVida.textContent = juego.vida;
  elValorOro.textContent = juego.oro;
  elValorPuntos.textContent = juego.puntuacion;
  elValorOleada.textContent = `${juego.numeroOleada} / ${juego.totalOleadas}`;

  btnDeshacer.disabled = !juego.historial.hayParaDeshacer();
  btnRehacer.disabled = !juego.historial.hayParaRehacer();
  btnIniciarOleada.disabled = juego.oleadaEnCurso || juego.colaOleadas.estaVacia() || juego.terminado;

  elRegistro.innerHTML = juego.registro.map((m) => `<li>${m}</li>`).join("");
  actualizarSeleccionTorres();
}

function mostrarMensaje(texto) {
  elMensaje.textContent = texto;
}

function mostrarFinDeJuego() {
  detenerCicloQuantum();
  tituloFinal.textContent = juego.gano ? "🎉 ¡Victoria!" : "💀 Derrota";
  detalleFinal.textContent = juego.gano
    ? `Defendiste la fuente de energia. Puntuacion final: ${juego.puntuacion}.`
    : `Los Cozy llegaron a la fuente de energia. Puntuacion final: ${juego.puntuacion}.`;
  overlayFinal.classList.remove("oculto");
}

// ----------------------------------------------------------------------
// Ciclo de quantum (motor del juego)
// ----------------------------------------------------------------------
function tickQuantum() {
  if (pausado || juego.terminado) return;
  const { eventos } = juego.simularQuantum();

  for (const ev of eventos) {
    switch (ev.tipo) {
      case "oleada-inicio":
        ev.enemigos.forEach(crearElementoEnemigo);
        mostrarMensaje(`🌊 Oleada ${ev.numero} en camino...`);
        break;
      case "movimiento":
        actualizarPosicionEnemigo(ev.enemigo);
        break;
      case "muerte":
        eliminarEnemigo(ev.enemigo, "muerte");
        break;
      case "fuga":
        eliminarEnemigo(ev.enemigo, "fuga");
        break;
      case "oleada-fin":
        mostrarMensaje(`✅ Oleada ${ev.numero} superada.`);
        break;
      case "victoria":
      case "derrota":
        mostrarFinDeJuego();
        break;
    }
  }
  actualizarUI();
}

function iniciarCicloQuantum() {
  detenerCicloQuantum();
  const ms = quantumBaseMs / velocidadFactor;
  document.documentElement.style.setProperty("--quantum-ms", `${ms}ms`);
  intervaloId = setInterval(tickQuantum, ms);
}
function detenerCicloQuantum() {
  if (intervaloId) clearInterval(intervaloId);
  intervaloId = null;
}

// ----------------------------------------------------------------------
// Botones de control
// ----------------------------------------------------------------------
btnModoColocar.addEventListener("click", () => cambiarModo("colocar"));
btnModoMejorar.addEventListener("click", () => cambiarModo("mejorar"));

btnDeshacer.addEventListener("click", () => {
  juego.deshacer();
  resincronizarTorres();
  actualizarUI();
});
btnRehacer.addEventListener("click", () => {
  juego.rehacer();
  resincronizarTorres();
  actualizarUI();
});

btnIniciarOleada.addEventListener("click", () => {
  if (!juego.oleadaEnCurso && !juego.colaOleadas.estaVacia()) {
    tickQuantum(); // dispara de inmediato el inicio de la siguiente oleada
  }
});

btnPausa.addEventListener("click", () => {
  pausado = !pausado;
  btnPausa.textContent = pausado ? "▶ Reanudar" : "⏸ Pausar";
});

btnVelocidad.addEventListener("click", () => {
  velocidadFactor = velocidadFactor === 1 ? 2 : 1;
  btnVelocidad.textContent = `${velocidadFactor}x`;
  iniciarCicloQuantum();
});

btnReiniciar.addEventListener("click", reiniciarJuego);
btnJugarDeNuevo.addEventListener("click", reiniciarJuego);

// ----------------------------------------------------------------------
// Arranque / reinicio
// ----------------------------------------------------------------------
function reiniciarJuego() {
  detenerCicloQuantum();
  overlayFinal.classList.add("oculto");
  elCapaEntidades.innerHTML = "";
  pausado = false;
  btnPausa.textContent = "⏸ Pausar";
  velocidadFactor = 1;
  btnVelocidad.textContent = "1x";
  modo = "colocar";

  mapa = new Mapa(RUTA, FILAS, COLUMNAS);
  juego = new Juego(mapa, VIDA_INICIAL, ORO_INICIAL, TOTAL_OLEADAS);

  construirTablero();
  construirPanelTorres();
  cambiarModo("colocar");
  mostrarMensaje("Coloca tus primeras torres y pulsa «Iniciar oleada».");
  actualizarUI();
  iniciarCicloQuantum();
}

reiniciarJuego();
