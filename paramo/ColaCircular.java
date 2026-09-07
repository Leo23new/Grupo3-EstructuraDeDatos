package paramo;

/**
 * TDA Cola Circular genérica, implementada con arreglo dinámico.
 *
 * Se usa para simular el movimiento de los Cozy a lo largo de la ruta
 * mediante "quantums" de tiempo (estilo round-robin de un planificador
 * de procesos): cada Cozy se desencola, se actualiza su estado, y si
 * sigue con vida y no ha llegado al final de la ruta, vuelve a
 * encolarse al final para su próximo turno.
 *
 * @param <T> tipo de elemento almacenado (en este proyecto: Cozy)
 */
public class ColaCircular<T> {

    private Object[] datos;
    private int frente;
    private int cantidad;
    private int capacidad;

    public ColaCircular(int capacidadInicial) {
        this.capacidad = Math.max(capacidadInicial, 4);
        this.datos = new Object[this.capacidad];
        this.frente = 0;
        this.cantidad = 0;
    }

    public ColaCircular() {
        this(16);
    }

    public boolean estaVacia() {
        return cantidad == 0;
    }

    public int tamanio() {
        return cantidad;
    }

    /** Inserta un elemento al final de la cola (encolar). */
    public void encolar(T elemento) {
        if (cantidad == capacidad) {
            redimensionar();
        }
        int posicionInsercion = (frente + cantidad) % capacidad;
        datos[posicionInsercion] = elemento;
        cantidad++;
    }

    /** Extrae y retorna el elemento al frente de la cola (desencolar). */
    @SuppressWarnings("unchecked")
    public T desencolar() {
        if (estaVacia()) {
            throw new IllegalStateException("No se puede desencolar: la cola circular está vacía");
        }
        T elemento = (T) datos[frente];
        datos[frente] = null;
        frente = (frente + 1) % capacidad;
        cantidad--;
        return elemento;
    }

    /** Consulta (sin remover) el elemento al frente de la cola. */
    @SuppressWarnings("unchecked")
    public T verFrente() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola circular está vacía");
        }
        return (T) datos[frente];
    }

    private void redimensionar() {
        int nuevaCapacidad = capacidad * 2;
        Object[] nuevosDatos = new Object[nuevaCapacidad];
        for (int i = 0; i < cantidad; i++) {
            nuevosDatos[i] = datos[(frente + i) % capacidad];
        }
        datos = nuevosDatos;
        frente = 0;
        capacidad = nuevaCapacidad;
    }
}
