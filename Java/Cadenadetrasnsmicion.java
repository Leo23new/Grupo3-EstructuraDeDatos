package Java;

public class Cadenadetrasnsmicion {

    public String estornudarSobreObjeto(Persona persona, Objeto objeto) {
        validarPersona(persona);
        validarObjeto(objeto);

        if (!persona.isInfectado()) {
            return persona.getNombre() + " no esta infectado y  " + objeto.getNombre() + " estaba limpio.";
        }

        objeto.contaminar();
        return persona.getNombre() + " ha estornudado sobre " + objeto.getNombre() + ", contaminándolo.";
    }

    public String tocarObjeto(Persona persona, Objeto objeto) {
        validarPersona(persona);
        validarObjeto(objeto);

        if (!objeto.isInfectado()) {
            persona.contaminar();
            return persona.getNombre() + " ha tocado " + objeto.getNombre() + ", contaminándose.";
        } else {
            return persona.getNombre() + " ha tocado " + objeto.getNombre() + ", pero no se ha contaminado.";
        }
    }

    public String tocarRostro(Persona persona) {
        validarPersona(persona);
        persona.tocarRostro();

        if (persona.isInfectado()) {
            return persona.getNombre() + " se ha tocado el rostro y se ha infectado.";
        } else {
            return persona.getNombre() + " se ha tocado el rostro pero no se ha infectado.";
        }
    }

    private void validarPersona(Persona persona) {
        if (persona == null) {
            throw new IllegalArgumentException("La persona no puede ser nula");
        }
    }

    private void validarObjeto(Objeto objeto) {
        if (objeto == null) {
            throw new IllegalArgumentException("El objeto no puede ser nulo");
        }
    }
}