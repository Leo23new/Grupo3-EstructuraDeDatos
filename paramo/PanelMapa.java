package paramo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * PanelMapa: dibuja el mapa (ruta, torres, Cozy) y traduce los clics
 * del mouse en coordenadas de grilla (fila, columna) para que
 * JuegoGUI decida qué hacer (colocar torre o seleccionarla).
 */
public class PanelMapa extends JPanel {

    public interface ClickCasillaListener {
        void onClickCasilla(int fila, int columna);
    }

    private static final int TAMANIO_CELDA = 56;

    private final Mapa mapa;
    private List<Cozy> cozysActuales = java.util.Collections.emptyList();
    private int[] posicionesCozy = new int[0];
    private Torre torreSeleccionada;

    public PanelMapa(Mapa mapa, ClickCasillaListener listener) {
        this.mapa = mapa;
        setPreferredSize(new Dimension(mapa.getColumnas() * TAMANIO_CELDA, mapa.getFilas() * TAMANIO_CELDA));
        setBackground(new Color(235, 240, 230));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int columna = e.getX() / TAMANIO_CELDA;
                int fila = e.getY() / TAMANIO_CELDA;
                if (fila >= 0 && fila < mapa.getFilas() && columna >= 0 && columna < mapa.getColumnas()) {
                    listener.onClickCasilla(fila, columna);
                }
            }
        });
    }

    public void actualizarEnemigos(List<Cozy> cozysActuales, int[] posicionesCozy) {
        this.cozysActuales = cozysActuales;
        this.posicionesCozy = posicionesCozy;
        repaint();
    }

    public void setTorreSeleccionada(Torre torre) {
        this.torreSeleccionada = torre;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        dibujarCasillasBase(g2);
        dibujarRuta(g2);
        dibujarFuenteDeCalor(g2);
        dibujarTorres(g2);
        dibujarCozys(g2);
        dibujarRangoSeleccion(g2);
    }

    private void dibujarCasillasBase(Graphics2D g2) {
        g2.setColor(new Color(214, 224, 205));
        for (int f = 0; f < mapa.getFilas(); f++) {
            for (int c = 0; c < mapa.getColumnas(); c++) {
                g2.fillRect(c * TAMANIO_CELDA, f * TAMANIO_CELDA, TAMANIO_CELDA, TAMANIO_CELDA);
            }
        }
        g2.setColor(new Color(190, 200, 180));
        for (int f = 0; f <= mapa.getFilas(); f++) {
            g2.drawLine(0, f * TAMANIO_CELDA, mapa.getColumnas() * TAMANIO_CELDA, f * TAMANIO_CELDA);
        }
        for (int c = 0; c <= mapa.getColumnas(); c++) {
            g2.drawLine(c * TAMANIO_CELDA, 0, c * TAMANIO_CELDA, mapa.getFilas() * TAMANIO_CELDA);
        }
    }

    private void dibujarRuta(Graphics2D g2) {
        g2.setColor(new Color(168, 150, 122));
        for (Ruta.Casilla casilla : mapa.getRuta().getCasillas()) {
            g2.fillRect(casilla.columna * TAMANIO_CELDA + 2, casilla.fila * TAMANIO_CELDA + 2,
                    TAMANIO_CELDA - 4, TAMANIO_CELDA - 4);
        }
    }

    private void dibujarFuenteDeCalor(Graphics2D g2) {
        Ruta ruta = mapa.getRuta();
        Ruta.Casilla destino = ruta.getCasilla(ruta.longitud() - 1);
        g2.setColor(new Color(230, 100, 40));
        int x = destino.columna * TAMANIO_CELDA;
        int y = destino.fila * TAMANIO_CELDA;
        g2.fillOval(x + 8, y + 8, TAMANIO_CELDA - 16, TAMANIO_CELDA - 16);
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 11f));
        g2.drawString("Calor", x + 8, y + TAMANIO_CELDA / 2 + 4);
    }

    private void dibujarTorres(Graphics2D g2) {
        for (Torre torre : mapa.getTorresColocadas()) {
            int x = torre.getColumna() * TAMANIO_CELDA;
            int y = torre.getFila() * TAMANIO_CELDA;
            g2.setColor(colorDeTorre(torre));
            g2.fillRoundRect(x + 6, y + 6, TAMANIO_CELDA - 12, TAMANIO_CELDA - 12, 12, 12);
            g2.setColor(torre == torreSeleccionada ? Color.YELLOW : Color.DARK_GRAY);
            g2.setStroke(new BasicStroke(torre == torreSeleccionada ? 3f : 1.5f));
            g2.drawRoundRect(x + 6, y + 6, TAMANIO_CELDA - 12, TAMANIO_CELDA - 12, 12, 12);
            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 12f));
            g2.drawString("Nv" + torre.getNivel(), x + 16, y + TAMANIO_CELDA / 2 + 5);
        }
    }

    private Color colorDeTorre(Torre torre) {
        if (torre instanceof TorrePiedraKanari) return new Color(120, 110, 100);
        if (torre instanceof TorreDeFuego) return new Color(200, 70, 40);
        if (torre instanceof TorreDeViento) return new Color(90, 160, 170);
        return Color.GRAY;
    }

    private void dibujarRangoSeleccion(Graphics2D g2) {
        if (torreSeleccionada == null) return;
        g2.setColor(new Color(255, 255, 0, 60));
        int alcance = torreSeleccionada.getAlcance();
        for (int f = 0; f < mapa.getFilas(); f++) {
            for (int c = 0; c < mapa.getColumnas(); c++) {
                if (torreSeleccionada.estaEnRango(f, c)) {
                    g2.fillRect(c * TAMANIO_CELDA, f * TAMANIO_CELDA, TAMANIO_CELDA, TAMANIO_CELDA);
                }
            }
        }
    }

    private void dibujarCozys(Graphics2D g2) {
        Ruta ruta = mapa.getRuta();
        for (int i = 0; i < cozysActuales.size(); i++) {
            Cozy cozy = cozysActuales.get(i);
            int pos = posicionesCozy[i];
            if (pos < 0 || pos >= ruta.longitud()) continue;
            Ruta.Casilla casilla = ruta.getCasilla(pos);
            int x = casilla.columna * TAMANIO_CELDA;
            int y = casilla.fila * TAMANIO_CELDA;

            g2.setColor(cozy.getTipo().equals("Cozy Robusto") ? new Color(150, 40, 160) : new Color(60, 130, 60));
            g2.fillOval(x + 14, y + 14, TAMANIO_CELDA - 28, TAMANIO_CELDA - 28);

            int anchoBarra = TAMANIO_CELDA - 16;
            double proporcion = Math.max(0, (double) cozy.getPv() / cozy.getPvMax());
            g2.setColor(Color.RED);
            g2.fillRect(x + 8, y + 6, anchoBarra, 4);
            g2.setColor(Color.GREEN);
            g2.fillRect(x + 8, y + 6, (int) (anchoBarra * proporcion), 4);
        }
    }
}
