package calendario.api.aplicacion;

import java.time.LocalDate;

// Define cómo se transfieren los datos de CalendarioEntidad
public class CalendarioDTO {
    private LocalDate fecha;
    private String nombre;
    private String tipo;
    

    // Constructor vacío
    public CalendarioDTO() {}

    // Constructor con parámetros
    public CalendarioDTO(LocalDate fecha, String nombre, String tipo) {
        this.fecha = fecha;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    // Getters y Setters
    public LocalDate getFecha() { return fecha; }

    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre;}

    public String getTipo() { return tipo; }

    public void setTipo(String tipo) { this.tipo = tipo;}

}
