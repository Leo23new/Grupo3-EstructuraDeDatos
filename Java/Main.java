package Java;

public class Main {
public static void main(String[] args) {
        Persona persona1 = new Persona("Juan", false, false);
        Persona persona2 = new Persona("Maria", true, true);
        Objeto objeto1 = new Objeto("Mesa", false);
        Objeto objeto2 = new Objeto("Silla", true);

        Cadenadetrasnsmicion cadena = new Cadenadetrasnsmicion();

        System.out.println(cadena.estornudarSobreObjeto(persona1, objeto1));
        System.out.println(cadena.tocarObjeto(persona2, objeto2));
        System.out.println(cadena.tocarRostro(persona1));

        persona1.mostrarEstado();
        persona2.mostrarEstado();
        objeto1.mostrarEstado();
        objeto2.mostrarEstado();
    }
}