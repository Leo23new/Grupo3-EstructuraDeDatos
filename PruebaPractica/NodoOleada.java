public class NodoOleada {
    private Oleada oleada;
    private NodoOleada siguiente;

    public NodoOleada(Oleada oleada) {
        this.oleada = oleada;
        this.siguiente = null;
    }

    public Oleada getOleada() { return oleada; }
    public NodoOleada getSiguiente() { return siguiente; }
    public void setSiguiente(NodoOleada siguiente) { this.siguiente = siguiente; }
}