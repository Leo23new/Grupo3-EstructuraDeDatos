public class ListaCircularOleadas {
    private NodoOleada ultimo;   // referencia a ultimo nodo registrado
    private NodoOleada actual;   // puntero a la oleada actual del juego
    private int cantidad;

    public ListaCircularOleadas() {
        ultimo = null;
        actual = null;
        cantidad = 0;
    }
    public void registrarOleada(Oleada o) {
        NodoOleada nuevo = new NodoOleada(o);
        if (ultimo == null) {
            ultimo = nuevo;
            nuevo.setSiguiente(nuevo); 
            actual = nuevo;
        } else {
            nuevo.setSiguiente(ultimo.getSiguiente()); 
            ultimo.setSiguiente(nuevo);                
            ultimo = nuevo;                            
        }
        cantidad++;
    }
    public void mostrarOleadas() {
        if (ultimo == null) {
            System.out.println("No hay oleadas registradas.");
            return;
        }
        NodoOleada primero = ultimo.getSiguiente();
        NodoOleada temp = primero;
        System.out.println("--- Oleadas registradas (" + cantidad + ") ---");
        int i = 0;
        do {
            System.out.println(temp.getOleada());
            temp = temp.getSiguiente();
            i++;
        } while (temp != primero && i < cantidad);
    }
    public Oleada avanzarSiguienteOleada() {
        if (actual == null) return null;
        Oleada o = actual.getOleada();
        actual = actual.getSiguiente();
        return o;
    }
    public void reiniciarCiclo() {
        if (ultimo != null) {
            actual = ultimo.getSiguiente();
        }
    }

    public Oleada getOleadaActual() {
        return (actual == null) ? null : actual.getOleada();
    }

    public int getCantidad() { return cantidad; }
    public boolean estaVacia() { return ultimo == null; }
}