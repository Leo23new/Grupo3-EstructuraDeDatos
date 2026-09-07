package paramo;

import javax.swing.*;
import java.util.List;

public class MainGUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            List<Mapa> catalogo = MapaFactory.construirCatalogo();
            String[] nombres = catalogo.stream().map(Mapa::getNombre).toArray(String[]::new);

            String seleccion = (String) JOptionPane.showInputDialog(
                    null,
                    "Selecciona un mapa:",
                    "Defensa del Paramo",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    nombres,
                    nombres[0]);

            Mapa mapaElegido = catalogo.get(0);
            if (seleccion != null) {
                for (Mapa m : catalogo) {
                    if (m.getNombre().equals(seleccion)) {
                        mapaElegido = m;
                        break;
                    }
                }
            }

            JuegoGUI ventana = new JuegoGUI(mapaElegido, /*totalOleadas*/ 6, /*vidaInicial*/ 100, /*oroInicial*/ 200);
            ventana.setVisible(true);
        });
    }
}
