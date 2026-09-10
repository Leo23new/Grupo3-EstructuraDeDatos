
public class ListaDobleEnemigos {
    private NodoEnemigo primero;
    private NodoEnemigo ultimo;
    private int cantidad;

    public ListaDobleEnemigos() {
        primero = null;
        ultimo = null;
        cantidad = 0;
    }

    /** Inserta un enemigo al final de la lista. */
    public void insertarAlFinal(Enemigo e) {
        NodoEnemigo nuevo = new NodoEnemigo(e);
        if (primero == null) {
            primero = nuevo;
            ultimo = nuevo;
        } else {
            nuevo.setAnterior(ultimo);
            ultimo.setSiguiente(nuevo);
            ultimo = nuevo;
        }
        cantidad++;
    }

    /** Elimina un enemigo destruido (u otro) buscandolo por id. */
    public boolean eliminarEnemigo(int id) {
        NodoEnemigo actual = primero;
        while (actual != null) {
            if (actual.getEnemigo().getId() == id) {
                NodoEnemigo anterior = actual.getAnterior();
                NodoEnemigo siguiente = actual.getSiguiente();

                if (anterior != null) anterior.setSiguiente(siguiente);
                else primero = siguiente;

                if (siguiente != null) siguiente.setAnterior(anterior);
                else ultimo = anterior;

                cantidad--;
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    /** Busca un enemigo por id. */
    public Enemigo buscarPorId(int id) {
        NodoEnemigo actual = primero;
        while (actual != null) {
            if (actual.getEnemigo().getId() == id) return actual.getEnemigo();
            actual = actual.getSiguiente();
        }
        return null;
    }

    /** Recorre la lista de enemigos hacia adelante (de primero a ultimo). */
    public void recorrerAdelante() {
        if (primero == null) {
            System.out.println("No hay enemigos activos.");
            return;
        }
        System.out.println("--- Enemigos (adelante) ---");
        NodoEnemigo actual = primero;
        while (actual != null) {
            System.out.println(actual.getEnemigo());
            actual = actual.getSiguiente();
        }
    }

    /** Recorre la lista de enemigos hacia atras (de ultimo a primero). */
    public void recorrerAtras() {
        if (ultimo == null) {
            System.out.println("No hay enemigos activos.");
            return;
        }
        System.out.println("--- Enemigos (atras) ---");
        NodoEnemigo actual = ultimo;
        while (actual != null) {
            System.out.println(actual.getEnemigo());
            actual = actual.getAnterior();
        }
    }

    /** Actualiza la posicion de cada enemigo segun su velocidad (avance de turno). */
    public void actualizarPosiciones() {
        NodoEnemigo actual = primero;
        while (actual != null) {
            Enemigo e = actual.getEnemigo();
            e.setPosicion(e.getPosicion() + e.getVelocidad());
            actual = actual.getSiguiente();
        }
    }

    public NodoEnemigo getPrimero() { return primero; }
    public NodoEnemigo getUltimo() { return ultimo; }
    public int getCantidad() { return cantidad; }
    public boolean estaVacia() { return primero == null; }
}