/**
 * Lista secuencial de torres
 * Lista secuencial manual (arreglo + contador), SIN usar ArrayList ni colecciones de java.util.
 */
public class ListaSecuencialTorres {
    private static final int CAPACIDAD_MAX = 50;
    private Torre[] torres;
    private int cantidad;

    public ListaSecuencialTorres() {
        torres = new Torre[CAPACIDAD_MAX];
        cantidad = 0;
    }

    /** Inserta una torre al final del arreglo. */
    public boolean insertarTorre(Torre t) {
        if (cantidad >= torres.length) {
            System.out.println("No se pueden registrar mas torres, capacidad maxima alcanzada.");
            return false;
        }
        torres[cantidad] = t;
        cantidad++;
        return true;
    }

    /** Elimina una torre por id, recorriendo y desplazando el arreglo. */
    public boolean eliminarTorrePorId(int id) {
        int indice = buscarIndice(id);
        if (indice == -1) return false;
        for (int i = indice; i < cantidad - 1; i++) {
            torres[i] = torres[i + 1];
        }
        torres[cantidad - 1] = null;
        cantidad--;
        return true;
    }

    private int buscarIndice(int id) {
        for (int i = 0; i < cantidad; i++) {
            if (torres[i].getId() == id) return i;
        }
        return -1;
    }

    /** Busca una torre por id. Retorna null si no existe. */
    public Torre buscarTorrePorId(int id) {
        int indice = buscarIndice(id);
        return (indice == -1) ? null : torres[indice];
    }

    /** Muestra todas las torres registradas. */
    public void mostrarTorres() {
        if (cantidad == 0) {
            System.out.println("No hay torres registradas.");
            return;
        }
        System.out.println("--- Torres registradas (" + cantidad + ") ---");
        for (int i = 0; i < cantidad; i++) {
            System.out.println(torres[i]);
        }
    }

    /** Cuenta cuantas torres estan activas (todas las almacenadas se consideran activas). */
    public int contarActivas() {
        int contador = 0;
        for (int i = 0; i < cantidad; i++) {
            if (torres[i].isActiva()) contador++;
        }
        return contador;
    }

    public Torre[] getTorres() { return torres; }
    public int getCantidad() { return cantidad; }
}