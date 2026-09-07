# Defensa de Torres — Cozy Invasion (versión web interactiva)

Versión en HTML/CSS/JavaScript del caso de estudio, pensada para correr con
la extensión **Live Server** de VS Code.

## Cómo ejecutarlo

1. Abre la carpeta `torres_defensa_web` en VS Code.
2. Instala la extensión **Live Server** (Ritwick Dey) si no la tienes.
3. Clic derecho sobre `index.html` → **"Open with Live Server"**.
4. Se abrirá en `http://127.0.0.1:5500/...` (o el puerto que uses).

**Importante:** el proyecto usa módulos de JavaScript (`import`/`export`),
por lo que el navegador los bloquea por CORS si abres `index.html`
directamente con doble clic (`file://...`). Por eso es necesario un
servidor local como Live Server (o `npx serve`, `python -m http.server`, etc.).

## Cómo jugar

- **Colocar**: elige una torre del panel izquierdo y haz clic en una celda
  libre (fuera del camino) para colocarla. Cada torre cuesta oro.
- **Mejorar**: cambia al modo "⬆️ Mejorar" y haz clic sobre una torre ya
  colocada para subirla de nivel (más daño y alcance) a cambio de oro.
- **Deshacer / Rehacer**: revierten o vuelven a aplicar la última colocación
  o mejora de torre.
- **Iniciar oleada**: lanza la siguiente oleada de Cozy.
- **Pausar / Velocidad**: pausan la simulación o la aceleran a 2x.
- Si un Cozy llega a la fuente de energía, pierdes una vida. Si tu vida
  llega a 0, pierdes la partida; si sobrevives a todas las oleadas, ganas.

## Estructura del proyecto y mapeo a las estructuras de datos pedidas

```
index.html
css/estilos.css
js/
 ├─ estructuras.js   Cola, ColaCircular y Pila (genéricas)
 ├─ entidades.js      Cozy (enemigo), Torre, Oleada
 ├─ mapa.js           Ruta predefinida + interpolación de posición
 ├─ comandos.js       Patrón Comando (colocar/mejorar) + GestorHistorial
 ├─ juego.js          Lógica pura del juego (sin DOM)
 └─ main.js           Tablero, interacción y animación (usa el DOM)
```

- **`ColaCircular`** (en `estructuras.js`) guarda a los Cozy activos en la
  ruta. En cada *quantum* (`Juego.simularQuantum()`), se procesa **una
  vuelta completa** de la cola en modo round-robin: se aplica el daño de
  las torres en rango, se avanza al enemigo, y solo se vuelve a encolar si
  sigue vivo y no ha llegado a la fuente de energía.
- **`Cola`** gestiona el orden de las oleadas (`Juego.colaOleadas`).
- **`Pila`** ×2, dentro de `GestorHistorial`, implementan deshacer/rehacer
  sobre los comandos `ComandoColocarTorre` y `ComandoMejorarTorre`
  (patrón Comando).
- `Juego` no toca el DOM: `simularQuantum()` devuelve una lista de eventos
  (`movimiento`, `muerte`, `fuga`, `oleada-inicio`, `oleada-fin`,
  `victoria`, `derrota`) que `main.js` usa para animar el tablero. Esto es
  lo mismo que la versión Java de consola, solo que aquí los "prints" se
  convierten en animaciones.

Esta misma lógica es equivalente, clase por clase, a la versión Java del
proyecto (`Cola.java`, `ColaCircular.java`, `Pila.java`, `Cozy.java`,
`Torre.java`, `Oleada.java`, `Mapa.java`, `ComandoTorre.java`,
`GestorHistorial.java`, `Juego.java`), así que puedes explicar ambas
implementaciones con el mismo diseño en tu sustentación.
