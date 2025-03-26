package calendario.api.aplicacion;

import java.time.LocalDate;

// Define cómo se transfieren los datos de CalendarioEntidad
public class CalendarioDTO {
    private LocalDate fecha;
    private String descripcion;

    // Constructor vacío
    public CalendarioDTO() {}

    // Constructor con parámetros
    public CalendarioDTO(LocalDate fecha, String descripcion) {
        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
