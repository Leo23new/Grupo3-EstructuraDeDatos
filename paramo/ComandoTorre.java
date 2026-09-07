package paramo;

/**
 * ComandoTorre: interfaz del patrón Command. Cada acción del jugador
 * sobre las torres (colocar, mejorar) sabe cómo ejecutarse y cómo
 * deshacerse a sí misma, lo cual permite un undo/redo simétrico y sin
 * ambigüedades, sin importar qué tipo de acción haya sido.
 */
public interface ComandoTorre {

    /** Aplica la acción (colocar la torre o subir su nivel). */
    void ejecutar();

    /** Revierte exactamente lo que hizo ejecutar(). */
    void deshacer();

    /** Descripción legible para mostrar en el historial de la UI. */
    String descripcion();
}
