package paramo;

/**
 * TDA Cola simple (FIFO) basada en nodos enlazados.
 * Se usa para gestionar el orden de llegada de las oleadas de Cozy.
 *
 * @param <T> tipo de elemento almacenado (en este proyecto: Oleada)
 */
public class ColaSimple<T> {

    private static class Nodo<T> {
        T valor;
        Nodo<T> siguiente;
        Nodo(T valor) {
            this.valor = valor;
        }
    }

    private Nodo<T> frente;
    private Nodo<T> final_;
    private int cantidad;

    public boolean estaVacia() {
        return cantidad == 0;
    }

    public int tamanio() {
        return cantidad;
    }

    public void encolar(T elemento) {
        Nodo<T> nuevo = new Nodo<>(elemento);
        if (final_ == null) {
            frente = nuevo;
            final_ = nuevo;
        } else {
            final_.siguiente = nuevo;
            final_ = nuevo;
        }
        cantidad++;
    }

    public T desencolar() {
        if (estaVacia()) {
            throw new IllegalStateException("No se puede desencolar: la cola de oleadas está vacía");
        }
        T valor = frente.valor;
        frente = frente.siguiente;
        if (frente == null) {
            final_ = null;
        }
        cantidad--;
        return valor;
    }
}
