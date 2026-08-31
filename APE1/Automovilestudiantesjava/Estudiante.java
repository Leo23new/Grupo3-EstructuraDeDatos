package Automovilestudiantesjava;
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
    // Calcular edad
    public int getEdad() {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    // Buscar una posición libre en el vector de notas
    public int buscarPosicionLibre() {

        for (int i = 0; i < notas.length; i++) {
            if (notas[i] == 0) {
                return i;
            }
        }

        return -1;
    }

    // Agregar nota
    public boolean agregarNota(double nota) {

        int posicion = buscarPosicionLibre();

        if (posicion == -1) {
            return false;
        }

        notas[posicion] = nota;
        return true;
    }

    // Modificar nota
    public boolean modificarNota(int posicion, double nuevaNota) {

        if (posicion < 0 || posicion >= notas.length) {
            return false;
        }

        if (notas[posicion] == 0) {
            return false;
        }

        notas[posicion] = nuevaNota;
        return true;
    }

    // Eliminar nota
    public boolean eliminarNota(int posicion) {

        if (posicion < 0 || posicion >= notas.length) {
            return false;
        }

        if (notas[posicion] == 0) {
            return false;
        }

        // Mover las notas siguientes hacia atras
        for (int i = posicion; i < notas.length - 1; i++) {
            notas[i] = notas[i + 1];
        }

        notas[notas.length - 1] = 0;

        return true;
    }

    // Cantidad de notas registradas
    public int cantidadNotas() {

        int cantidad = 0;

        for (int i = 0; i < notas.length; i++) {
            if (notas[i] != 0) {
                cantidad++;
            }
        }

        return cantidad;
    }

    // Calcular promedio del estudiante
    public double calcularPromedio() {

        double suma = 0;
        int cantidad = 0;

        for (int i = 0; i < notas.length; i++) {

            if (notas[i] != 0) {
                suma += notas[i];
                cantidad++;
            }
        }

        if (cantidad == 0) {
            return 0;
        }

        return suma / cantidad;
    }

    // Mostrar datos
    public void mostrarDatos() {

        System.out.println("Cedula: " + cedula);
        System.out.println("Nombres: " + nombres);
        System.out.println("Apellidos: " + apellidos);
        System.out.println("Fecha de nacimiento: " + fechaNacimiento);
        System.out.println("Edad: " + getEdad());
    }
}
