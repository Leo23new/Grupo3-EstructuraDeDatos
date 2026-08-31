import java.time.LocalDate;
import java.time.Period;

public class Estudiante{
        // Atributos
    private String cedula;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;

    // Vector para maximo notas
    private double[] notas;

        // Constructor
    public Estudiante(String cedula, String nombres, String apellidos,
                      LocalDate fechaNacimiento) {

        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;

        // Maximo 7 notas
        this.notas = new double[7];
    }

    // Getters
    public String getCedula() {
        return cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public double[] getNotas() {
        return notas;
    }

    // Setters
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

}