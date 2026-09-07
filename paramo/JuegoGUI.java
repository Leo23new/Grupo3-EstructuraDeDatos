package paramo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * JuegoGUI: interfaz gráfica en Swing. Toda la lógica del juego
 * (Mapa, GestorOleadas, HistorialAcciones, SimuladorRuta) es
 * exactamente la misma que usa la versión de consola (Juego.java);
 * esta clase solo agrega la capa visual y traduce clics/botones en
 * llamadas a esa misma lógica.
 */
public class JuegoGUI extends JFrame {

    private enum ModoClic { SELECCIONAR, COLOCAR_KANARI, COLOCAR_FUEGO, COLOCAR_VIENTO }

    private final Mapa mapa;
    private final GestorOleadas gestorOleadas;
    private final HistorialAcciones historial;
    private final SimuladorRuta simulador;

    private int vidaJugador;
    private int oro;
    private int puntuacion;
    private boolean juegoTerminado = false;
    private ModoClic modoActual = ModoClic.SELECCIONAR;
    private Torre torreSeleccionada;

    private final PanelMapa panelMapa;
    private final JLabel etiquetaEstado;
    private final JTextArea areaLog;
    private final JButton botonMejorar;
    private final JButton botonAutoPlay;
    private javax.swing.Timer timerAutoPlay;

    public JuegoGUI(Mapa mapa, int totalOleadas, int vidaInicial, int oroInicial) {
        super("Defensa del Paramo - Cozy y la Fuente de Calor Ancestral");
        this.mapa = mapa;
        this.gestorOleadas = new GestorOleadas(totalOleadas);
        this.historial = new HistorialAcciones();
        this.simulador = new SimuladorRuta(mapa);
        this.vidaJugador = vidaInicial;
        this.oro = oroInicial;
        this.puntuacion = 0;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        etiquetaEstado = new JLabel();
        etiquetaEstado.setFont(etiquetaEstado.getFont().deriveFont(Font.BOLD, 15f));
        etiquetaEstado.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        add(etiquetaEstado, BorderLayout.NORTH);

        panelMapa = new PanelMapa(mapa, this::onClicCasilla);
        JPanel envoltorioMapa = new JPanel(new FlowLayout(FlowLayout.CENTER));
        envoltorioMapa.add(panelMapa);
        add(envoltorioMapa, BorderLayout.CENTER);

        areaLog = new JTextArea(8, 30);
        areaLog.setEditable(false);
        add(new JScrollPane(areaLog), BorderLayout.SOUTH);

        JPanel panelControles = new JPanel();
        panelControles.setLayout(new BoxLayout(panelControles, BoxLayout.Y_AXIS));
        panelControles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelControles.add(new JLabel("Modo de colocacion:"));
        ButtonGroup grupoModo = new ButtonGroup();
        panelControles.add(crearBotonModo("Seleccionar / mejorar torre", ModoClic.SELECCIONAR, grupoModo, true));
        panelControles.add(crearBotonModo("Colocar Torre de Piedra Kañari (50 oro)", ModoClic.COLOCAR_KANARI, grupoModo, false));
        panelControles.add(crearBotonModo("Colocar Torre de Fuego (75 oro)", ModoClic.COLOCAR_FUEGO, grupoModo, false));
        panelControles.add(crearBotonModo("Colocar Torre de Viento (60 oro)", ModoClic.COLOCAR_VIENTO, grupoModo, false));

        panelControles.add(Box.createVerticalStrut(16));
        botonMejorar = new JButton("Mejorar torre seleccionada");
        botonMejorar.addActionListener(this::alMejorarTorre);
        botonMejorar.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelControles.add(botonMejorar);

        panelControles.add(Box.createVerticalStrut(16));
        JButton botonDeshacer = new JButton("Deshacer (undo)");
        botonDeshacer.addActionListener(this::alDeshacer);
        botonDeshacer.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelControles.add(botonDeshacer);

        JButton botonRehacer = new JButton("Rehacer (redo)");
        botonRehacer.addActionListener(this::alRehacer);
        botonRehacer.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelControles.add(botonRehacer);

        panelControles.add(Box.createVerticalStrut(16));
        JButton botonPaso = new JButton("Avanzar 1 quantum");
        botonPaso.addActionListener(e -> avanzarQuantum());
        botonPaso.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelControles.add(botonPaso);

        botonAutoPlay = new JButton("Iniciar auto-avance");
        botonAutoPlay.addActionListener(this::alAlternarAutoPlay);
        botonAutoPlay.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelControles.add(botonAutoPlay);

        add(panelControles, BorderLayout.EAST);

        actualizarVista();
        registrarLog("Bienvenido al Paramo. Coloca torres para proteger la Fuente de Calor.");

        pack();
        setLocationRelativeTo(null);
    }

    private JRadioButton crearBotonModo(String texto, ModoClic modo, ButtonGroup grupo, boolean seleccionado) {
        JRadioButton boton = new JRadioButton(texto, seleccionado);
        boton.setAlignmentX(Component.LEFT_ALIGNMENT);
        boton.addActionListener(e -> {
            modoActual = modo;
            if (modo != ModoClic.SELECCIONAR) {
                torreSeleccionada = null;
                panelMapa.setTorreSeleccionada(null);
            }
        });
        grupo.add(boton);
        return boton;
    }

    private void onClicCasilla(int fila, int columna) {
        if (juegoTerminado) return;

        if (modoActual == ModoClic.SELECCIONAR) {
            Torre encontrada = buscarTorreEn(fila, columna);
            torreSeleccionada = encontrada;
            panelMapa.setTorreSeleccionada(encontrada);
            botonMejorar.setEnabled(encontrada != null);
            if (encontrada != null) {
                registrarLog("Seleccionada: " + encontrada);
            }
            return;
        }

        if (!mapa.esPosicionValida(fila, columna)) {
            JOptionPane.showMessageDialog(this, "Posicion invalida: fuera del mapa, sobre la ruta, o ya ocupada.");
            return;
        }

        Torre nuevaTorre = crearTorreSegunModo(fila, columna);
        if (oro < nuevaTorre.getCosto()) {
            JOptionPane.showMessageDialog(this, "Oro insuficiente. Necesitas " + nuevaTorre.getCosto() + ", tienes " + oro + ".");
            return;
        }

        oro -= nuevaTorre.getCosto();
        historial.ejecutar(new ComandoColocarTorre(mapa, nuevaTorre));
        registrarLog("Colocada: " + nuevaTorre);
        actualizarVista();
    }

    private Torre crearTorreSegunModo(int fila, int columna) {
        switch (modoActual) {
            case COLOCAR_FUEGO: return new TorreDeFuego(fila, columna);
            case COLOCAR_VIENTO: return new TorreDeViento(fila, columna);
            default: return new TorrePiedraKanari(fila, columna);
        }
    }

    private Torre buscarTorreEn(int fila, int columna) {
        for (Torre t : mapa.getTorresColocadas()) {
            if (t.getFila() == fila && t.getColumna() == columna) {
                return t;
            }
        }
        return null;
    }

    private void alMejorarTorre(ActionEvent e) {
        if (torreSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Primero selecciona una torre en el mapa.");
            return;
        }
        int costo = torreSeleccionada.getCostoMejora();
        if (oro < costo) {
            JOptionPane.showMessageDialog(this, "Oro insuficiente para mejorar. Necesitas " + costo + ".");
            return;
        }
        oro -= costo;
        historial.ejecutar(new ComandoMejorarTorre(torreSeleccionada));
        registrarLog("Mejorada: " + torreSeleccionada);
        actualizarVista();
    }

    private void alDeshacer(ActionEvent e) {
        String descripcion = historial.deshacer();
        torreSeleccionada = null;
        panelMapa.setTorreSeleccionada(null);
        botonMejorar.setEnabled(false);
        registrarLog(descripcion != null ? "Deshecho: " + descripcion : "No hay acciones para deshacer.");
        actualizarVista();
    }

    private void alRehacer(ActionEvent e) {
        String descripcion = historial.rehacer();
        torreSeleccionada = null;
        panelMapa.setTorreSeleccionada(null);
        botonMejorar.setEnabled(false);
        registrarLog(descripcion != null ? "Rehecho: " + descripcion : "No hay acciones para rehacer.");
        actualizarVista();
    }

    private void alAlternarAutoPlay(ActionEvent e) {
        if (timerAutoPlay != null && timerAutoPlay.isRunning()) {
            timerAutoPlay.stop();
            botonAutoPlay.setText("Iniciar auto-avance");
        } else {
            timerAutoPlay = new javax.swing.Timer(700, ev -> avanzarQuantum());
            timerAutoPlay.start();
            botonAutoPlay.setText("Detener auto-avance");
        }
    }

    private void avanzarQuantum() {
        if (juegoTerminado) return;

        if (simulador.hayCozysEnRuta()) {
            SimuladorRuta.ResultadoQuantum resultado = simulador.avanzarQuantum();
            puntuacion += resultado.puntosGanados;
            oro += resultado.puntosGanados / 2;
            vidaJugador -= resultado.vidaPerdida;
            for (String evento : resultado.eventos) {
                registrarLog(evento);
            }
        } else if (gestorOleadas.hayOleadasPendientes()) {
            Oleada oleada = gestorOleadas.siguienteOleada();
            for (Cozy cozy : oleada.getCozys()) {
                simulador.encolarCozy(cozy);
            }
            registrarLog("Comienza la oleada " + oleada.getNumero() + " con " + oleada.getCozys().size() + " Cozy.");
        } else {
            registrarLog("Victoria. Sobreviviste todas las oleadas del paramo.");
            finalizarJuego();
        }

        if (vidaJugador <= 0 && !juegoTerminado) {
            registrarLog("La Fuente de Calor Ancestral se ha apagado. Fin del juego.");
            finalizarJuego();
        }

        actualizarVista();
    }

    private void finalizarJuego() {
        juegoTerminado = true;
        if (timerAutoPlay != null) {
            timerAutoPlay.stop();
        }
    }

    private void actualizarVista() {
        List<Cozy> cozysEnPantalla = simulador.snapshotCozys();
        int[] posiciones = new int[cozysEnPantalla.size()];
        for (int i = 0; i < cozysEnPantalla.size(); i++) {
            posiciones[i] = cozysEnPantalla.get(i).getPosicionEnRuta();
        }
        panelMapa.actualizarEnemigos(cozysEnPantalla, posiciones);

        etiquetaEstado.setText(String.format(
                "<html>Vida: %d &nbsp;|&nbsp; Oro: %d &nbsp;|&nbsp; Oleada: %d/%d &nbsp;|&nbsp; Puntuacion: %d &nbsp;|&nbsp; Cozy en ruta: %d</html>",
                Math.max(vidaJugador, 0), oro, gestorOleadas.getOleadasGeneradas(), gestorOleadas.getTotalOleadas(),
                puntuacion, simulador.cantidadCozysEnRuta()));
    }

    private void registrarLog(String mensaje) {
        areaLog.append(mensaje + "\n");
        areaLog.setCaretPosition(areaLog.getDocument().getLength());
    }
}
