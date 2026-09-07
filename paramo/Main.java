package paramo;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8);
        List<Mapa> catalogo = MapaFactory.construirCatalogo();

        System.out.println("Selecciona un mapa:");
        for (int i = 0; i < catalogo.size(); i++) {
            System.out.println((i + 1) + ". " + catalogo.get(i).getNombre());
        }
        System.out.print("Opcion: ");
        int seleccion = 1;
        try {
            seleccion = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException ignored) {
            // se usa el mapa 1 por defecto
        }
        if (seleccion < 1 || seleccion > catalogo.size()) {
            seleccion = 1;
        }

        Mapa mapaElegido = catalogo.get(seleccion - 1);
        Juego juego = new Juego(mapaElegido, /*totalOleadas*/ 6, /*vidaInicial*/ 100, /*oroInicial*/ 200, sc);
        juego.iniciar();
        sc.close();
    }
}
