package paramo;

/**
 * TDA Pila (LIFO) genérica basada en nodos enlazados.
 * Se usa para el historial de deshacer/rehacer de colocación y mejora
 * de torres.
 *
 * @param <T> tipo de elemento almacenado (en este proyecto: ComandoTorre)
 */
public class PilaGenerica<T> {

    private static class Nodo<T> {
        T valor;
        Nodo<T> anterior;
        Nodo(T valor, Nodo<T> anterior) {
            this.valor = valor;
            this.anterior = anterior;
        }
    }

    private Nodo<T> tope;
    private int cantidad;

    public boolean estaVacia() {
        return cantidad == 0;
    }

    public int tamanio() {
        return cantidad;
    }

    public void apilar(T elemento) {
        tope = new Nodo<>(elemento, tope);
        cantidad++;
    }

    public T desapilar() {
        if (estaVacia()) {
            throw new IllegalStateException("No se puede desapilar: la pila está vacía");
        }
        T valor = tope.valor;
        tope = tope.anterior;
        cantidad--;
        return valor;
    }

    public T verTope() {
        if (estaVacia()) {
            throw new IllegalStateException("La pila está vacía");
        }
        return tope.valor;
    }

    /** Vacía completamente la pila (se usa al invalidar el historial de rehacer). */
    public void vaciar() {
        tope = null;
        cantidad = 0;
    }
}
